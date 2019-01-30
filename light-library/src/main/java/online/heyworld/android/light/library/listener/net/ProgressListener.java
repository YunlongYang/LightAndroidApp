package online.heyworld.android.light.library.listener.net;

public interface ProgressListener {
    ProgressListener NOP = new ProgressListener() {
        @Override
        public void onStart(long allLength) {

        }

        @Override
        public void onProgress(long nowLength, long allLength) {

        }

    };

    void onStart(long allLength);

    void onProgress(long nowLength,long allLength);
}
