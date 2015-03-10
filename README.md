GraphAware Neo4j UUID
=====================

GraphAware UUID is a simple library that transparently assigns a UUID to newly created nodes in the graph and makes sure nobody
can (accidentally or intentionally) change or delete them.

Getting the Software
--------------------

### Server Mode

When using Neo4j in the <a href="http://docs.neo4j.org/chunked/stable/server-installation.html" target="_blank">standalone server</a> mode,
you will need the <a href="https://github.com/graphaware/neo4j-framework" target="_blank">GraphAware Neo4j Framework</a> and GraphAware Neo4j UUID .jar files (both of which you can <a href="http://graphaware.com/downloads/" target="_blank">download here</a>) dropped
into the `plugins` directory of your Neo4j installation. After changing a few lines of config (read on) and restarting Neo4j, the module will do its magic.

#### Releases

Releases are synced to <a href="http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22uuid%22" target="_blank">Maven Central repository</a>. When using Maven for dependency management, include the following dependency in your pom.xml.

    <dependencies>
        ...
        <dependency>
            <groupId>com.graphaware.neo4j</groupId>
            <artifactId>uuid</artifactId>
            <version>2.1.7.28.7</version>
        </dependency>
        ...
    </dependencies>

#### Snapshots

To use the latest development version, just clone this repository, run `mvn clean install` and change the version in the
dependency above to 2.1.7.28.8-SNAPSHOT.

#### Note on Versioning Scheme

The version number has two parts. The first four numbers indicate compatibility with Neo4j GraphAware Framework.
 The last number is the version of the UUID library. For example, version 2.1.3.11.1 is version 1 of the UUID library
 compatible with GraphAware Neo4j Framework 2.1.3.11.


Setup and Configuration
--------------------

### Server Mode

Edit neo4j.properties to register the UUID module:

```
com.graphaware.runtime.enabled=true

#UIDM becomes the module ID:
com.graphaware.module.UIDM.1=com.graphaware.module.uuid.UuidBootstrapper

#optional, default is uuid:
com.graphaware.module.UIDM.uuidProperty=uuid

#optional, default is all nodes:
com.graphaware.module.UIDM.node=hasLabel('Label1') || hasLabel('Label2')

```

Note that "UIDM" becomes the module ID. 

`com.graphaware.module.UIDM.uuidProperty` is the property name that will be used to store the assigned UUID on the node. The default is "uuid".

`com.graphaware.module.UIDM.nodes` specifies either a fully qualified class name of [`NodeInclusionPolicy`](http://graphaware.com/site/framework/latest/apidocs/com/graphaware/common/policy/NodeInclusionPolicy.html) implementation,
or a Spring Expression Language expression determining, which nodes to assign a UUID to. The default is to assign the
UUID property to every node which isn't internal to the framework.

Using GraphAware UUID
---------------------

Apart from the configuration described above, the GraphAware UUID module requires nothing else to function. It will assign a UUID to nodes configured,
and will prevent modifications to the UUID or deletion of the UUID property from these nodes by not allowing the transaction to commit.

You may access the UUID via your own API's or Cypher- the GraphAware UUID module does not at this point provide an API to retrieve a node by UUID.
This is because there is no efficient way to do this except via indexing, which is currently waiting on 
<a href="https://github.com/neo4j/neo4j/issues/2714" target="_blank">issue 2714</a>


License
-------

Copyright (c) 2014 GraphAware

GraphAware is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.
If not, see <http://www.gnu.org/licenses/>.