/*
 * Copyright (c) 2014 GraphAware
 *
 * This file is part of GraphAware.
 *
 * GraphAware is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.graphaware.module.uuid;

import com.graphaware.runtime.config.TxDrivenModuleConfiguration;
import com.graphaware.runtime.module.BaseTxDrivenModule;
import com.graphaware.runtime.module.DeliberateTransactionRollbackException;
import com.graphaware.tx.event.improved.api.Change;
import com.graphaware.tx.event.improved.api.ImprovedTransactionData;
import com.graphaware.tx.executor.batch.IterableInputBatchTransactionExecutor;
import com.graphaware.tx.executor.batch.UnitOfWork;
import com.graphaware.tx.executor.single.TransactionCallback;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.tooling.GlobalGraphOperations;

public class UuidModule extends BaseTxDrivenModule<Void> {

    private final static int BATCH_SIZE = 1000;

    private final UuidGenerator uuidGenerator;
    private final UuidConfiguration uuidConfiguration;
    private final String UUID_PROPERTY;

    public UuidModule(String moduleId, UuidConfiguration configuration) {
        super(moduleId);
        this.uuidGenerator = new EaioUuidGenerator();
        this.uuidConfiguration = configuration;
        this.UUID_PROPERTY = uuidConfiguration.getUuidProperty();
    }

    @Override
    public TxDrivenModuleConfiguration getConfiguration() {
        return uuidConfiguration;
    }

    @Override
    public void initialize(GraphDatabaseService database) {

        new IterableInputBatchTransactionExecutor<>(
                database,
                BATCH_SIZE,
                new TransactionCallback<Iterable<Node>>() {
                    @Override
                    public Iterable<Node> doInTransaction(GraphDatabaseService database) throws Exception {
                        return GlobalGraphOperations.at(database).getAllNodes();
                    }
                },
                new UnitOfWork<Node>() {
                    @Override
                    public void execute(GraphDatabaseService database, Node node, int batchNumber, int stepNumber) {
                        if (getConfiguration().getInclusionPolicies().getNodeInclusionPolicy().include(node)) {
                            assignUuid(node);
                        }
                    }
                }
        ).execute();
    }

    @Override
    public Void beforeCommit(ImprovedTransactionData transactionData) throws DeliberateTransactionRollbackException {

        //Set the UUID on all created nodes
        for (Node node : transactionData.getAllCreatedNodes()) {
            assignUuid(node);
        }

        //Check if the UUID has been modified or removed from the node and throw an error if so
        for (Change<Node> change : transactionData.getAllChangedNodes()) {
            rollbackIfRemoved(change);
            rollbackIfModified(change);
        }

        return null;
    }

    /**
     * If the node doesn't already have a uuid property, we want to create one and set it
     * @param node
     */
    private void assignUuid(Node node) {
        // TODO
    }

    private void rollbackIfModified(Change<Node> change) {
        // TODO
    }

    private void rollbackIfRemoved(Change<Node> change) {
        // TODO
    }
}
