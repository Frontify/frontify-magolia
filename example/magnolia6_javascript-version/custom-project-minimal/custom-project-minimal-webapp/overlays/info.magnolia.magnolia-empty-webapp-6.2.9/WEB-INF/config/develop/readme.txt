Profile for local development:

* Place the repository (magnolia.repositories.home) outside of the target directory do mvn clean doesn't wipe it out.
* Point the file system directory for light-modules (magnolia.resources.dir) to ./src/main/magnolia
* Enable development mode (magnolia.develop)
* Sticker indicating the active profile in the Admin Central UI

To activate this profile either set a system property or an environment variable MAGNOLIA_PROFILE=develop