  
# Underwriting Blockchain 

Prerequisites - 
* Language - Java (tested with jdk1.8.0_241.jdk)
* Database - Postgres 12.2
* Build Tool - Gradle (tested with gradle 6.3)
* BlockChain Framework - Corda (version 4.3)
* UI - (HTML 5, Jquery)



# Blockchain description( Abstract)

Abstract:
Blockchain based Underwriting engine for transparency and fraud detection using distributed ledger including multiple 
external data. To reduce the risk liability.

The main idea here is to have a greater transparency of underwriting data for a given entity which could be vehicle, 
a person, a house and all entities for which the insurance can be provided using a blockchain based systems. 
Where more parties can form a blockchain and share the data of underwriting to other trusted blockchain nodes.

For the sake of POC we are restricting our project to the health insurance related underwriting

In this project we have 4 nodes-
1. Notary (Governing body as per the corda framework)
2. Insurace company (Requester for the data for a given ssn)
3. Health Organization( Data provider for the health related underwriting)
4. Credit History Organization( Data provider for the credit history for the given ssn)


Corda related prerequisites

# Corda related Pre-Requisites

See https://docs.corda.net/getting-set-up.html.

# Usage

## Running tests inside IntelliJ
	
We recommend editing your IntelliJ preferences so that you use the Gradle runner - this means that the quasar utils
plugin will make sure that some flags (like ``-javaagent`` - see below) are
set for you.

To switch to using the Gradle runner:

* Navigate to ``Build, Execution, Deployment -> Build Tools -> Gradle -> Runner`` (or search for `runner`)
  * Windows: this is in "Settings"
  * MacOS: this is in "Preferences"
* Set "Delegate IDE build/run actions to gradle" to true
* Set "Run test using:" to "Gradle Test Runner"

If you would prefer to use the built in IntelliJ JUnit test runner, you can run ``gradlew installQuasar`` which will
copy your quasar JAR file to the lib directory. You will then need to specify ``-javaagent:lib/quasar.jar``
and set the run directory to the project root directory for each test.

## Running the nodes

See https://docs.corda.net/tutorial-cordapp.html#running-the-example-cordapp.

## Interacting with the nodes

### Shell

When started via the command line, each node will display an interactive shell:

    Welcome to the Corda interactive shell.
    Useful commands include 'help' to see what is available, and 'bye' to shut down the node.
    
    Tue Nov 06 11:58:13 GMT 2018>>>

You can use this shell to interact with your node. For example, enter `run networkMapSnapshot` to see a list of 
the other nodes on the network:

    Tue Nov 06 11:58:13 GMT 2018>>> run networkMapSnapshot
    [
      {
      "addresses" : [ "localhost:10002" ],
      "legalIdentitiesAndCerts" : [ "O=Notary, L=London, C=GB" ],
      "platformVersion" : 3,
      "serial" : 1541505484825
    },
      {
      "addresses" : [ "localhost:10005" ],
      "legalIdentitiesAndCerts" : [ "O=PartyA, L=London, C=GB" ],
      "platformVersion" : 3,
      "serial" : 1541505382560
    },
      {
      "addresses" : [ "localhost:10008" ],
      "legalIdentitiesAndCerts" : [ "O=PartyB, L=New York, C=US" ],
      "platformVersion" : 3,
      "serial" : 1541505384742
    }
    ]
    
    Tue Nov 06 12:30:11 GMT 2018>>> 

You can find out more about the node shell [here](https://docs.corda.net/shell.html).

### Client

`clients/src/main/java/com/template/Client.java` defines a simple command-line client that connects to a node via RPC 
and prints a list of the other nodes on the network.

#### Running the client

##### Via the command line

Run the `runTemplateClient` Gradle task. By default, it connects to the node with RPC address `localhost:10006` with 
the username `user1` and the password `test`.

##### Via IntelliJ

Run the `Run Template Client` run configuration. By default, it connects to the node with RPC address `localhost:10006` 
with the username `user1` and the password `test`.

### Webserver

`clients/src/main/java/com/template/webserver/` defines a simple Spring webserver that connects to a node via RPC and 
allows you to interact with the node over HTTP.

The API endpoints are defined here:

     clients/src/main/java/com/template/webserver/Controller.java

And a static webpage is defined here:

     clients/src/main/resources/static/

#### Running the webserver

##### Via the command line

Run the `runTemplateServer` Gradle task. By default, it connects to the node with RPC address `localhost:10006` with 
the username `user1` and the password `test`, and serves the webserver on port `localhost:10050`.

##### Via IntelliJ

Run the `Run Template Server` run configuration. By default, it connects to the node with RPC address `localhost:10006` 
with the username `user1` and the password `test`, and serves the webserver on port `localhost:10050`.

#### Interacting with the webserver

The static webpage is served on:

    http://localhost:10050

While the sole template endpoint is served on:

    http://localhost:10050/templateendpoint
    
# Extending the template

You should extend this template as follows:

* Add your own state and contract definitions under `contracts/src/main/java/`
* Add your own flow definitions under `workflows/src/main/java/`
* Extend or replace the client and webserver under `clients/src/main/java/`

For a guided example of how to extend this template, see the Hello, World! tutorial 
[here](https://docs.corda.net/hello-world-introduction.html).



# Database

You need to create 4 separate database for the for nodes

create database underwriting_notary;
create user notary with encrypted password 'password';
grant all privileges on database underwriting_notary to notary;


create database underwriting_insurance;
create user insurance with encrypted password 'password';
grant all privileges on database underwriting_insurance to insurance;


create database underwriting_credit;
create user ncb with encrypted password 'password';
grant all privileges on database underwriting_credit to ncb;

create database underwriting_nho;
create user nho with encrypted password 'password';
grant all privileges on database underwriting_nho to nho;


# Node related config files to update the above created databases

Node related config files can be found at config/dev/<node-folder>

After you build the nodes please copy these configs from the above folder to the 
individual node folder in the /build/nodes/<node-folder>

