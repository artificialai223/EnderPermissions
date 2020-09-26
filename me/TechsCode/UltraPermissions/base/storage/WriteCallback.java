

package me.TechsCode.EnderPermissions.base.storage;

public interface WriteCallback
{
    void onSuccess();
    
    void onFailure(final Exception p0);
    
    public static class EmptyWriteCallback implements WriteCallback
    {
        @Override
        public void onSuccess() {
        }
        
        @Override
        public void onFailure(final Exception ex) {
        }
    }
}
