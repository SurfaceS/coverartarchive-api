## Deployment
Run `mvn deploy -P release`.

You can use the `gpg.keyname` property in the Maven settings.xml to specify which GPG key to use to sign the files, as described at https://central.sonatype.org/publish/requirements/gpg/#listing-keys

On macOS you might need to symlink `gpg2` to `gpg` as described at https://stackoverflow.com/a/69920288/2049714