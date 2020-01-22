# Mainframe Dataset Reader

The project is about mainframe record datasets reading and (for now) displaying the data with specified encoding

## Getting started

Prerequisites:

1. You need to have Java 11 installed on your machine
2. You need to have Gradle 5 installed on your machine
3. You need to have COBOL QSAM dataset according to rules, described below

### Installing

- Clone the project from the version control system:

`git clone ...`

- Go to the project root:

`cd data-file-reader`

- execute the gradle task to build the application jar file:

`gradle clean jar --info`

- now, you can find the jar file in this path: <project_root>/build/libs/---.jar

### Running tests

- execute the gradle task to run tests:

`gradle clean test` 

### Build with

- Gradle (dependency management and build system)

### Authors

- Imbirev Nikolai (creator and supporter)

### Versioning

- Now, the 1.1-SNAPSHOT version is released with simple displaying of records amount and raw records data 
with specified encoding
- In development stage: 1.2-SNAPSHOT version with more specific fields displaying and working by COBOL copybook (
https://git.epam.com/epm-lstr/lemon/copybook-parser)
- In plans: 1.3-SNAPSHOT version with supporting new copybook fields (according to the
updates in Copybook parser library)
- ...

### Acknowledges

Dataset limitations:

- Only QSAM datasets are supported with sequential records disposition
- If some records are incomplete with real data, you need to fill them with zeroes or spaces to specified the record lengths
- For now, only alphanumeric and numeric fields are supported, groups are not available
- Default dataset encoding is US_ASCII, but you can specify another one

### Licensing

The License file can be found in project root (LICENSE.txt)