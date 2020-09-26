

package me.TechsCode.EnderPermissions.base.update;

import java.net.URLDecoder;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.io.File;
import java.net.URL;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class UpdateServer
{
    public static UpdateResponse request(final TechPlugin<?> techPlugin, final String str, final String str2) {
        try {
            final URL url = new URL(str + "/" + techPlugin.getName() + "/download?uid=" + str2);
            final File tempFile = File.createTempFile("update", null);
            FileUtils.copyURLToFile(url, tempFile);
            final String s = FileUtils.readLines(tempFile, "UTF-8").get(0);
            if (s.equalsIgnoreCase("NOT-AUTHENTICATED")) {
                return new UpdateResponse(ResponseType.NOT_AUTHENTICATED, null);
            }
            if (s.equalsIgnoreCase("NOT-VERIFIED")) {
                return new UpdateResponse(ResponseType.NOT_VERIFIED, null);
            }
            if (s.equalsIgnoreCase("NOT-PURCHASED")) {
                return new UpdateResponse(ResponseType.NOT_PURCHASED, null);
            }
            return new UpdateResponse(ResponseType.SUCCESS, tempFile);
        }
        catch (IOException ex) {
            return new UpdateResponse(ResponseType.SERVER_OFFLINE, null);
        }
    }
    
    public static UpdateResponse requestAndPerform(final TechPlugin<?> techPlugin, final String s, final String s2) {
        final UpdateResponse request = request(techPlugin, s, s2);
        if (request.getType() == ResponseType.SUCCESS) {
            try {
                final File file = new File(URLDecoder.decode(techPlugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
                file.delete();
                FileUtils.moveFile(request.getFile(), file);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return request;
    }
}
