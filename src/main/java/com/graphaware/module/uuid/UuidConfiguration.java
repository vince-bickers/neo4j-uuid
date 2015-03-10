package com.graphaware.module.uuid;

import com.graphaware.common.policy.InclusionPolicies;
import com.graphaware.runtime.config.BaseTxDrivenModuleConfiguration;
import com.graphaware.runtime.policy.InclusionPoliciesFactory;

public class UuidConfiguration extends BaseTxDrivenModuleConfiguration<UuidConfiguration> {

    private static final String DEFAULT_UUID_PROPERTY = "uuid";

    private String uuidProperty;

    protected UuidConfiguration(InclusionPolicies inclusionPolicies) {
        super(inclusionPolicies);
    }

    public UuidConfiguration(InclusionPolicies inclusionPolicies, String uuidProperty) {
        super(inclusionPolicies);
        this.uuidProperty = uuidProperty;
    }

    public static UuidConfiguration defaultConfiguration() {
        return new UuidConfiguration(InclusionPoliciesFactory.allBusiness(), DEFAULT_UUID_PROPERTY);
    }

    @Override
    protected UuidConfiguration newInstance(InclusionPolicies inclusionPolicies) {
        return new UuidConfiguration(inclusionPolicies, getUuidProperty());
    }

    public String getUuidProperty() {
        return uuidProperty;
    }

    public UuidConfiguration withUuidProperty(String uuidProperty) {
        return new UuidConfiguration(getInclusionPolicies(), uuidProperty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UuidConfiguration that = (UuidConfiguration) o;

        if (!uuidProperty.equals(that.uuidProperty)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + uuidProperty.hashCode();
        return result;
    }
}
