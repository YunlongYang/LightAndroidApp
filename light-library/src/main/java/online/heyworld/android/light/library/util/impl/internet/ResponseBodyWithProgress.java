package online.heyworld.android.light.library.util.impl.internet;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import online.heyworld.android.light.library.listener.net.ProgressListener;

public class ResponseBodyWithProgress extends ResponseBody {

    private ProgressListener progressListener = ProgressListener.NOP;

    private ResponseBody sourceBody;
    private long nowLength;

    public ResponseBodyWithProgress(ResponseBody sourceBody) {
        this.sourceBody = sourceBody;
        nowLength = 0;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return sourceBody.contentType();
    }

    @Override
    public long contentLength() {
        return sourceBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(sourceBody.source()) {
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long allLength = contentLength();
                if(nowLength==0){
                    notifyProgressStart(allLength);
                }
                long bytesRead = super.read(sink, byteCount);
                nowLength += bytesRead == -1 ? 0 : bytesRead;
                notifyProgress(nowLength,contentLength());
                return bytesRead;
            }
        });
    }

    private void notifyProgress(long nowLength, long allLength) {
        progressListener.onProgress(nowLength,allLength);
    }

    private void notifyProgressStart(long allLength) {
        progressListener.onStart(allLength);
    }

}
