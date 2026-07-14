Install Oracle cli and ui
 - Issues:
    What is TNS?
    - Issues like adding services through tnsnames.ora(FREEPDB1)
    - Also changing listener.ora from IP to localhost

SYSDBA Vs SYSTEM

What is a service?

What is a container(DB)?
    What is PDB, CDB?

How to check users list, current user?
How to check PDBs list, current PDB or container?
How to check services list,current list of services linked to current container?
How to identify services which exist but doesnt run?
How to make a single Query to map these all?
How to list out tables created in that particular container?

What is a connection in the Oracle SQL Developer UI?
How to create .sql file and run through cli and UI?
Tablespaces?

Note:
    1. You can login through 3(or 2) ways mentioned in 1_SYSDBA_Vs_SYSTEM.
    2. A single container can have multiple Service, but not opposite way!
    3. Free service is linked to ROOT of CDB by default, and there are many other services of that Root of CDB.
    4. FREEPDB1 is the default service as well as PDB name of CDB.
    5. In free version, one cant create multiple CDB.

FRESH QNS:
1. What are the steps a developer must go through before creating a custom username, Service, PDB, tables and others(List out em through answering this Qns. for eg, schema(include abstraction, others) and other)
    - username 25BCE1087, service COLLEGE_PDB, tables as per LA and others for college LA as a practice