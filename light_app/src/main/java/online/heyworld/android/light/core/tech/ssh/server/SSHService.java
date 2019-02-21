package online.heyworld.android.light.core.tech.ssh.server;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ProcessShellFactory;

import java.io.IOException;

public class SSHService{
    private SshServer sshServer;
    public void init(){
        sshServer = SshServer.setUpDefaultServer();
        sshServer.setPasswordAuthenticator(new PasswordAuthenticator() {
            @Override
            public boolean authenticate(String username, String password, ServerSession session) throws PasswordChangeRequiredException, AsyncAuthException {
                return true;
            }
        });
        sshServer.setShellFactory(new ProcessShellFactory(new String[] { "/bin/sh", "-i", "-l" }));
        sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        sshServer.setPort(10022);
    }

    public void start(){
        try {
            sshServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
