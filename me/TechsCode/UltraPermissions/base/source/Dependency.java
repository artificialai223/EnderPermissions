

package me.TechsCode.EnderPermissions.base.source;

public class Dependency
{
    private String groupId;
    private String artifactId;
    private String version;
    
    public Dependency(final String groupId, final String artifactId, final String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }
    
    public String getGroupId() {
        return this.groupId;
    }
    
    public String getArtifactId() {
        return this.artifactId;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String toURL() {
        return this.groupId.replace(".", "/") + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar";
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Dependency && ((Dependency)o).toURL().equals(this.toURL());
    }
    
    @Override
    public int hashCode() {
        return this.toURL().hashCode();
    }
}
