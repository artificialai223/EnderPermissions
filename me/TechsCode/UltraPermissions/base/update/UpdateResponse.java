

package me.TechsCode.EnderPermissions.base.update;

import java.io.File;

public class UpdateResponse
{
    private ResponseType type;
    private File file;
    
    public UpdateResponse(final ResponseType type, final File file) {
        this.type = type;
        this.file = file;
    }
    
    public ResponseType getType() {
        return this.type;
    }
    
    public File getFile() {
        return this.file;
    }
}
