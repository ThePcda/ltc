//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.model;

public final class WorkspaceConfigImpl implements WorkspaceConfig {

    private String liferayHomeName;
    private String tomcatHomeName;
    private String[] includeArguments;

    protected WorkspaceConfigImpl(final String liferayHomeName, final String tomcatHomeName, final String[] includeArguments) {
        this.liferayHomeName = liferayHomeName;
        this.tomcatHomeName = tomcatHomeName;
        this.includeArguments = includeArguments;
    }

    @Override
    public final String getLiferayHomeName() {
        return liferayHomeName;
    }

    @Override
    public final String getTomcatHomeName() {
        return tomcatHomeName;
    }

    @Override
    public final String[] getIncludeArguments() {
        return includeArguments;
    }

}
