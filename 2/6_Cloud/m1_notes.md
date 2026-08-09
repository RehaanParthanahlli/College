# P1(Intro)
    Data, info stored in a digital format in a hardware. Eg, MP4, png, txt, md etc.

    Cloud, If someone wanna store, access, process some data over a remote location a proper infrastructure is required. So, the infrastructure
        provided over internet that allows storage, access and computational power is cloud.

    Computing, Its a continous process of converting ideas into instructions, into actions and give a output by machine(This entire workflow is called as computing). Its like brainworking to perform task or solve a problem.

    Cloud Computing, When a cloud service provide computational services like, storage, processing power, databases, networking, servers, ai or software application that particulare cloud service is called as cloud computing.

    Why Cloud Computing?,
    Before cloud computing existed, Companies had to go through a brutal cash burny workflow as below:
        - Buy hefty Servers(Powerful based on usecase), Install OS.
        - Then hire engineers(For Hardware maintenance, Security, Scalibility, Graceful degradation, Build Application/Database/Software over the hardware, for setting and managing the networking of that hardware) - So, lot of engineers cost money.
        - Then after setting up, they needed backup.
        - This was risky, as the company has to pay hefty amounts.
        - Also dont forget the electricity bill, more power consumption, more bill.
        ----- Migration from one server to another was difficult.
        - Companies had to buy more severs in order for horizontal scaling & more RAM, Storage in case of vertical scaling. Which is very expensive.
        - Also look after heating issue of servers, need to buy expensive coolants.

        But as cloud computing initiated,(These problems dissappeared),
            - Cloud computing solves all above problems.
            - One can enjoy these services based on usecase.
            - Its like someone added a skip option in real life.
            - Here, just request the computing resources, the cloud service providers analyzes the request then provides it to client.
        Analogy,
            - Just for running any electrical appliances, you dont need to build entire power plant. You just request that from the provider.
            - For eg, I need a server with 64 GB RAM, 1 TB Solid State Disk for one month.

    Q. Traditional(For traditional mention above points) Vs Cloud Computing(Counter these points).
# P2(Cloud Arch means, IaaS PaaS SaaS, Hybrid, Cloud Evolutions are just models for eg, Grid computing and so on Business Drivers, Evo, Grid, Serverless)
    Business Drivers for Cloud Migration
        1. Cost Reduction(Just explain the Traditional computing points like, cooling, power, resources, human resources)
        2. Scalibility(Vertical, horizontal, Manually in traditional with Engineers, here directly)
            For eg, flipkarts big billion days, Netflix(Any new film release)
        3. Reliability(It ensures continuity of a business even when a big crashes or failures through concepts like graceful degradation) 
            Automatic Backup, Security, Faster recovery of previous versions.
        4. Global Reach
            Its like deploying the application world wide through edge functions, reduced latency, Maintain privacy.
        5. Sustainibility
        6. Business Agility
            Ability to respond to a problem in market quickly.
                For eg, Just need a Idea, Build it locally, deploy quickly to real world & Monitor logs(Observability, logs-metrics(CPU Usage so on)-Traces).
        7. Innovations
            Immediate cost reduction for New Ideas involving ML Models, Agents, RAGs, MCP servers
    
    Tech behind cloud computing,(Traditional Computing, but accessed over internet)
        Distributed Systems(Multiple servers to perform a single task quickly)

        Virtualization(One big server, can run multiple containers to perform a activity)

        Networking

        Data Centers(Warehouse for large no of cloud infra(Server,Networking,Application running on a server, coolants))

        Automations(event driven functions, automatically allocate the resources)

    Evolution of computing
        Early Computing(1940-60, Military and Scientific) -- Traditional Computing, occupied entire room, enourmous maintenance and power consumption
            ↓
        Mainframes(1960-80, One large server for multiple users) -- Traditional Computing
            ↓
        Personal Computing(1980, Individual must own PC, Personalized access but lacked resources sharing)
            ↓
        Client-Server(1990, PC Client access application through hosted on centralized server, Improved performance, reliability, and scalability.)
            ↓
        Distributed Computing(1990, Multiple servers pooled to solve a single problem quickly)
            ↓
        Grid Computing(1990-2000, Computing resources from multiple independent systems were pooled to solve large-scale problems)
            ↓
        Virtualization(2000, One single server can run multiple virtual servers, containers, Reduced infrastructure cost and increased flexibility, dont forget concept of hypervisor)
            ↓
        Cloud Computing(2010s)
            ↓
        Serverless Computing(Edge functions, just needed to develop the application nothing else)

        Note: Grid(Less co-ordination) but distributed systems(More co-ordination)

    Grid computing
        1. Grid Computing
            The PPT defines Grid Computing as a distributed computing system where multiple computers connected through a network work together to solve a common problem.

            The mental model:

                        BIG PROBLEM
                            │
                            ↓
                        Control Node(Allocates Memory and divides task and assigns them to Providers,communicates with grid middleware, monitors execution and finally gives final result)
                            |
                            ↓
                        Grid Middleware(Software that connects the providers and manages communication between them)
                            │
                ┌───────────┼───────────┐
                ↓           ↓           ↓
            Computer A  Computer B  Computer C(Provider C)
                │           │           │
                Task 1      Task 2      Task 3
                │           │           │
                └───────────┼───────────┘
                            ↓
                        Final Result
            Analogy
                Imagine you have to count 10 million exam papers.

                One person:
                10,000,000 papers
                    ↓
                    Person
                    ↓
                    pain

                Ten thousand people:
                10,000,000
                    ↓
                Split into chunks
                    ↓
                Person 1 → 1,000
                Person 2 → 1,000
                Person 3 → 1,000
                ...
                    ↓
                Combine results

        2. How Grid Computing works

            1. Task Submission(Control node receives)
                    ↓
            2. Task Segmentation(Control node divides)
                    ↓
            3. Subtask Assignment(CN Assigns)
                    ↓
            4. Parallel Execution
                    ↓
            5. Communication / Coordination(Middleware)
                    ↓
            6. Result Aggregation(CN)

            The control node divides the job and distributes subtasks according to resource availability, capability, and workload.

        3. Grid Computing types

            Three types from your PPT:
            Computational Grid:
            Used for computationally intensive tasks.
                Examples:
                scientific simulations
                weather forecasting
                engineering calculations
            
            Scavenging Grid:
            Uses idle computing resources.
                Think:
                Your PC
                CPU utilization = 8%

                            ↓

                Grid uses idle capacity
            
            Data Grid:
            Connects multiple storage systems to share large amounts of data across locations.

        4. Grid vs Cloud
                Grid	                        Cloud
            Distributed ownership	        Usually centralized provider
            Resource sharing	            Service delivery
            Research-oriented historically	Commercial services
            Often batch-oriented	        On-demand
            User manages more resources	    Provider manages infrastructure
            Computational workloads	Broad   range of services

            Grid asks:
            "How can we combine many distributed computers to solve a large problem?"

            Cloud asks:
            "How can we provide computing resources as an on-demand service?"
        5. Merits and De-merits(Flexy, scaly, secu, relia, complex, latency, cost, performance, execution, parallel processing)
            1. Less flexible(Not ment for all tasks), More complex, Not easy communication(network issues), Security, Middleware(Beta)
            2. Less cost, Scalable, Faster execution(More performance, parallel processing)

    Serverless computing(These are even-driven architectures)
        1. Serverless Computing
        Now the interesting one.
        Serverless does not mean there are no servers.
        Servers absolutely exist. The provider manages them.

            The correct mental model is:

            Serverless = no server management by the developer.

                Developer
                ↓
                Buy/allocate server
                ↓
                Install OS
                ↓
                Configure network
                ↓
                Install runtime
                ↓
                Deploy application
                ↓
                Monitor CPU/RAM
                ↓
                Patch OS
                ↓
                Handle failures
                ↓
                Scale

                Instead:
                    Developer
                    ↓
                    Write function
                    ↓
                    Deploy function
                    ↓
                    Cloud
                    ↓
                    Provider manages infrastructure

        2. Serverless request flow

            This is the part you should actually understand.

            User
            │
            │ HTTP request
            ↓
            API Gateway / Trigger
            │
            ↓
            Function Runtime
            │
            ↓
            Your Function
            │
            ├── Database
            ├── External API
            └── Storage
            │
            ↓
            Response
            │
            ↓
            User

        3. Serverless characteristics

            According to your PPT:

            1. No server management
            Provider handles infrastructure.

            2. Automatic scaling
            10 users
            ↓
            small resource allocation

            1,000,000 users
            ↓
            more execution capacity

            3. Pay only when used
            Instead of paying for an idle server, execution is metered.

            4. Fast deployment
            No traditional infrastructure setup.

            5. High availability
            Provider distributes workloads and handles infrastructure failures.

            These characteristics are covered in the PPT.

        4. Serverless isn't magic

            There are trade-offs.

                Cold starts
                    A function that hasn't been used recently may need initialization time.

                    Request
                    ↓
                    Function not warm
                    ↓
                    Initialize runtime
                    ↓
                    Execute
                    ↓
                    Response
                    Vendor lock-in

            If your architecture heavily depends on provider-specific services, migration can become difficult.(As,
             DynamoDB(AWS DB)
                ↓
            Cosmos DB(Azure))

            Execution limits
            Functions aren't necessarily designed for arbitrary long-running workloads. The PPT uses AWS Lambda's execution limit as an example.

            Debugging Issue
# P3(Cloud Architecture - Cloud architectures are broadly categorized into deployment models—Public, Private, Hybrid, Community, and Multi‑Cloud—and service models like IaaS, PaaS, SaaS, and Serverless. The right choice depends on your organization’s needs for scalability, control, compliance, and cost. Eg,)
### 🌐 Example: E‑Commerce Cloud Architecture

**1. Frontend Layer (User Interface)**
- Web app and mobile app hosted on **Content Delivery Network (CDN)** for fast global access.
- Static assets (HTML, CSS, JS, images) stored in **Object Storage** (e.g., AWS S3, Azure Blob Storage).

**2. Application Layer**
- **API Gateway** to route requests securely.
- **Microservices** deployed in containers (e.g., Kubernetes, Azure AKS, AWS ECS).
- Services include:
  - Product catalog
  - Shopping cart
  - Payment processing
  - Order management

**3. Data Layer**
- **Relational Database** (e.g., PostgreSQL, MySQL) for transactional data like orders and payments.
- **NoSQL Database** (e.g., MongoDB, DynamoDB) for product catalog and user sessions.
- **Cache Layer** (e.g., Redis, Memcached) for fast retrieval of frequently accessed data.

**4. Security & Identity**
- **Identity Provider** (e.g., Azure AD, AWS Cognito) for authentication and authorization.
- **Encryption** for data at rest and in transit.
- **Web Application Firewall (WAF)** to protect against attacks.

**5. Monitoring & Logging**
- **Centralized Logging** (e.g., ELK stack, Azure Monitor).
- **Application Performance Monitoring** (e.g., New Relic, Datadog).
- **Alerts & Dashboards** for system health.

**6. Scalability & Reliability**
- **Auto-scaling groups** to handle traffic spikes.
- **Load Balancer** to distribute requests across servers.
- **Disaster Recovery** with backups and multi-region deployment.

---

### 🏗️ Visual Flow
- **User → CDN → API Gateway → Microservices → Databases/Cache**
- Supporting layers: **Security, Monitoring, Scaling**

### Web 1.0, 2.0, 3.0 & EDA(How to decide Arch)
#### 🏗️ Step 1: Application Style
- **Monolith:**  
  - ✅ Simple to build, deploy, and debug.  
  - ❌ Harder to scale and maintain as features grow.  
  - **Example:** A small e‑commerce site built with Django or Rails.  
- **Microservices:**  
  - ✅ Independent services, scalable, resilient.  
  - ❌ Requires DevOps maturity (CI/CD, monitoring, orchestration).  
  - **Example:** Netflix backend with hundreds of microservices running on Kubernetes.  

---

#### 🌐 Step 2: Web Generation
- **Web 2.0 (Centralized, interactive):**  
  - Focus on user‑generated content, SaaS apps, social media.  
  - **Example:** Facebook, Shopify, Slack.  
- **Web 3.0 (Decentralized, blockchain‑based):**  
  - Focus on smart contracts, tokenization, peer‑to‑peer.  
  - **Example:** Ethereum dApps, Uniswap, DAOs.  

---

#### 🔔 Step 3: Event‑Driven Architecture (EDA) or Not
- **EDA:**  
  - ✅ Real‑time responsiveness, loose coupling, scalable.  
  - ❌ More complex debugging and monitoring.  
  - **Example:** Uber ride updates → event triggers notifications, billing, and driver matching.  
- **Non‑EDA (Request/Response):**  
  - ✅ Easier to design and debug.  
  - ❌ Less suited for real‑time workloads.  
  - **Example:** A payroll system that processes monthly salary requests.  

---

#### ⚙️ Step 4: Stack Selection
- **Monolith + Web 2.0 + Non‑EDA:**  
  - Java/Spring Boot, Django, Rails.  
- **Microservices + Web 2.0 + EDA:**  
  - Node.js, Kafka, Docker/Kubernetes.  
- **Microservices + Web 3.0 + EDA:**  
  - Solidity (smart contracts), Ethereum, off‑chain microservices with event bus.  
- **Hybrid:**  
  - Monolith core with microservices extensions.  
  - Web 2.0 frontend with Web 3.0 payment modules.  

---

#### 🧭 Decision Framework Checklist
1. **Scale & Complexity:**  
   - Small app → Monolith.  
   - Large, evolving system → Microservices.  
2. **Business Model:**  
   - Centralized SaaS → Web 2.0.  
   - Decentralized finance, NFTs → Web 3.0.  
3. **Responsiveness:**  
   - Real‑time → EDA.  
   - Predictable workflows → Non‑EDA.  
4. **Stack:**  
   - Choose based on above decisions and team expertise.  

---

#### 📌 Example Walkthrough
Imagine you’re asked: *“We need to build a ride‑sharing app.”*  
- **Step 1:** Microservices (scalable, independent services for drivers, riders, payments).  
- **Step 2:** Web 2.0 (centralized platform).  
- **Step 3:** EDA (real‑time ride updates, notifications).  
- **Step 4:** Stack → Node.js + Kafka + Kubernetes + React frontend.  

👉 That’s exactly how engineers reason through architecture choices — systematically, with trade‑offs at each stage.
# P4(Goals & Benefits, Roles & Boundaries)
    Goals & Benefits(Latency, Cost, Scalibility, Security of Data, Recovery & Backup, Reliability)
        • Increased Responsiveness
        • Reduced Investments and Proportional Costs
        • Increased Scalability
        • Increased Availability and Reliability
        • High Speed – Quick Deployment
        • Automatic Software Updates and Integration
        • Data Security
        • Data Loss Prevention
        • Collaboration
        • Unlimited Storage Capacity

## Roles and Boundaries

Your PPT defines **multiple roles**, not just the five NIST performers.

### 1. Cloud Provider

The organization that **provides cloud IT resources/services** and maintains the underlying cloud infrastructure according to the agreed SLA.

Examples:

* AWS
* Azure
* GCP
* IBM Cloud
* Oracle Cloud



---

### 2. Cloud Consumer

The organization/person that has an arrangement with the provider to **use cloud resources/services**.

The consumer:

* selects services
* configures resources
* uses them
* monitors usage
* pays for consumption

 

**Example:**

```text
VIT → AWS
 ↑       ↑
Consumer Provider
```

---

### 3. Cloud Service Owner

This is the one I missed.

The **Cloud Service Owner** is the person/organization that **legally owns the cloud service**.

Important detail:

> The service owner can be either the **cloud consumer** or the **cloud provider**.



#### Example

Netflix builds its streaming service but hosts it on AWS.

```text
Netflix
   │
   │ owns Netflix service
   ↓
Netflix Streaming Service
   │
   │ hosted on
   ↓
AWS
```

So:

```text
Cloud Provider = AWS
Cloud Service Owner = Netflix
```

The PPT specifically gives Netflix, Gmail, Zoom, and online shopping websites as examples of cloud services owned by consumers but deployed on another party's cloud. 

---

### 4. Cloud Resource Administrator

The person/organization responsible for **administering cloud-based IT resources**, including cloud services.

It can belong to:

* Cloud Consumer
* Cloud Provider
* Third-party organization



Example:

```text
Company
   ↓
IT Administrator
   ↓
Manages cloud resources
```

---

### 5. Cloud Auditor

An independent third party that evaluates:

* Security controls
* Privacy
* Performance
* Cloud environment

The purpose is to provide an **unbiased assessment** and increase trust between consumer and provider. 

---

### 6. Cloud Broker

A party that manages/negotiates cloud service usage between consumers and providers.

Your PPT gives **three broker functions**:

| Function                   | Meaning                                                      |
| -------------------------- | ------------------------------------------------------------ |
| **Service Intermediation** | Adds capabilities to an existing cloud service               |
| **Service Aggregation**    | Combines services from multiple providers                    |
| **Service Arbitrage**      | Selects providers based on factors such as price/performance |



Example:

```text
                Broker
              /   |   \
            AWS Azure GCP
```

---

### 7. Cloud Carrier

Provides the **wire-level/network connectivity** between consumer and provider.

Think:

```text
Consumer
   ↓
Internet / ISP
   ↓
Cloud Provider
```

Examples from your PPT:

* Airtel
* Jio
* BSNL
* Vodafone Idea



---

## Boundaries

This is where my previous answer was too narrow.

Your PPT specifically defines **two boundaries**:

### A. Organizational Boundary

The logical or physical boundary separating an organization's **own IT resources** from the outside world.

It includes resources the organization owns/controls:

```text
Organizational Boundary
┌─────────────────────────────┐
│ Servers                     │
│ Databases                   │
│ Storage                     │
│ Networks                    │
│ Applications                │
└─────────────────────────────┘
```

It **does not mean the physical campus/building**. It refers to the organization's IT resources. 

#### Simple meaning

> **"What IT resources belong to and are controlled by this organization?"**

---

### B. Trust Boundary

The **Trust Boundary** is the logical limit up to which an organization trusts external IT resources/cloud services.

When you move your application/data to a cloud provider, you're crossing from your organization's controlled environment into infrastructure operated by someone else.



#### Example from your PPT

VIT hosts its ERP on Microsoft Azure:

```text
             VIT
┌─────────────────────────┐
│ ERP Users               │
│ Faculty PCs             │
│ Campus Network          │
└────────────┬────────────┘
             │
       TRUST BOUNDARY
             │
             ↓
┌─────────────────────────┐
│ Microsoft Azure         │
│                         │
│ ERP Database            │
│ Virtual Machines        │
│ Storage                 │
└─────────────────────────┘
```

The PPT identifies VIT's users/PCs/campus network on one side and Azure's database, VMs and storage on the other. 

---

### Complete Roles Cheat Sheet

This is what I'd put in your documentation:

| Role                             | Core responsibility                                     |
| -------------------------------- | ------------------------------------------------------- |
| **Cloud Provider**               | Provides and manages cloud infrastructure/services      |
| **Cloud Consumer**               | Uses cloud services/resources                           |
| **Cloud Service Owner**          | Legally owns the cloud service                          |
| **Cloud Resource Administrator** | Administers cloud resources/services                    |
| **Cloud Auditor**                | Independently evaluates security, privacy, performance  |
| **Cloud Broker**                 | Manages/negotiates services between consumers/providers |
| **Cloud Carrier**                | Provides network connectivity between consumer/provider |

 

### Boundaries

| Boundary                    | Meaning                                                                                    |
| --------------------------- | ------------------------------------------------------------------------------------------ |
| **Organizational Boundary** | Separates an organization's controlled IT resources from outside resources                 |
| **Trust Boundary**          | Separates resources the organization trusts from external resources/services it must trust |



---

### One mental model

Don't memorize seven isolated nouns. Think of an actual application:

```text
                    CLOUD ECOSYSTEM

       ┌───────────────────────────────────┐
       │          Cloud Consumer           │
       │                                   │
       │   Cloud Service Owner             │
       │   Resource Administrator          │
       │                                   │
       └───────────────┬───────────────────┘
                       │
                 Trust Boundary
                       │
                       ↓
                Cloud Provider
                       │
              ┌────────┴────────┐
              │                 │
         Cloud Services     Infrastructure
              │
              ↓
          Cloud Users

   Broker ← manages relationship/services → Provider

   Auditor → independently checks environment

   Carrier  → provides network connectivity
```

And the **two boundaries** answer two different questions:

> **Organizational boundary:** *What do we own/control?*

> **Trust boundary:** *What external resources are we trusting?*
# P5(Challenges & Characteristics)
## Cloud Characteristics + Challenges

| Characteristic                      | Short meaning                                                                | Main challenge                      |
| ----------------------------------- | ---------------------------------------------------------------------------- | ----------------------------------- |
| **On-Demand Usage**                 | Provision/manage resources whenever needed via portal/API                    | **Complex management**              |
| **Ubiquitous Access**               | Access services from anywhere through supported devices/networks             | **Internet dependency**             |
| **Multitenancy & Resource Pooling** | Multiple customers share infrastructure while remaining isolated             | **Security & privacy risks**        |
| **Elasticity**                      | Automatically scale resources up/down with demand                            | **Load-balancing complexity**       |
| **Measured Usage**                  | Monitor CPU, memory, storage, bandwidth, etc. for usage-based billing        | **Hidden costs / unexpected bills** |
| **Resiliency**                      | Continue operating despite infrastructure failures using redundancy/failover | **Downtime risks**                  |

The six characteristics are exactly the ones listed in your PPT, and the challenges above are drawn from its cloud-challenges section.  

## Other challenges in the PPT

Your slides also list:

* **Vendor lock-in**
* **Data transfer bottlenecks**
* **Limited technical expertise**
* **Real-time performance monitoring**
* **Environmental impact of hyperscale clouds**
* **Security misconfigurations**



## Memory shortcut

```text
Characteristics:
ON-DEMAND → ANYWHERE → SHARED → ELASTIC → MEASURED → RESILIENT

Challenges:
COST → INTERNET → SECURITY → SCALING → BILLING → FAILURE
```
# P6(Cloud delivery Models, IaaS..... & Cloud Deployment models)
## Cloud Service / Delivery Models

The PPT presents **IaaS, PaaS, SaaS** as the three main delivery layers, then additionally covers specialized models.  

| Model      | What you get                            | You mainly manage               | Example                   |
| ---------- | --------------------------------------- | ------------------------------- | ------------------------- |
| **IaaS**   | Virtual servers, storage, networking    | OS + runtime + app + data       | AWS EC2                   |
| **PaaS**   | Infrastructure + OS + runtime/platform  | Application + data              | Heroku, Google App Engine |
| **SaaS**   | Complete ready-to-use software          | Just use it                     | Google Workspace, Zoom    |
| **FaaS/Serverless Computing**   | Individual functions executed on events | Function code                   | AWS Lambda                |
| **BaaS**   | Ready-made backend services             | Mostly frontend/client          | Firebase                  |
| **DBaaS**  | Managed database                        | Data/schema/application usage   | Amazon RDS                |
| **SECaaS** | Cloud-based security services           | Security configuration/policies | Cloudflare Security       |

### Memory

```text
IaaS → Infrastructure
PaaS → Platform
SaaS → Software
FaaS → Function
BaaS → Backend
DBaaS → Database
SECaaS → Security
```

### Important distinction

```text
IaaS → PaaS → SaaS
        ↑
Provider manages MORE
Consumer manages LESS
```

The PPT's responsibility table shows this progression from infrastructure through application/data. 

---

## Cloud Deployment Models

A **deployment model** describes **where the cloud infrastructure is deployed, who owns it, and who can access it**. This is different from a service model, which describes **what service you receive**. 

| Deployment Model    | Ownership                                    | Access                   | Key idea                                       | Example                               |
| ------------------- | -------------------------------------------- | ------------------------ | ---------------------------------------------- | ------------------------------------- |
| **Public Cloud**    | Cloud provider                               | General public/customers | Shared infrastructure, scalable, pay-as-you-go | AWS, Azure, GCP                       |
| **Private Cloud**   | Single organization                          | That organization        | Greater control and isolation                  | VIT's internal cloud                  |
| **Community Cloud** | Multiple organizations/community or provider | Specific community       | Shared goals, policies, regulations            | Universities sharing cloud            |
| **Hybrid Cloud**    | Combination                                  | Mixed                    | Public + private working together              | Private student data + public website |

 

---

### 1. Public Cloud

```text
        Cloud Provider
       ┌───────────────┐
       │ Infrastructure│
       └───────┬───────┘
          ┌────┼────┐
          ↓    ↓    ↓
          A    B    C
```

* Provider owns/manages infrastructure.
* Multiple customers share it.
* Highly scalable.
* Usually pay-as-you-go.
* Examples: AWS, Azure, GCP. 

---

### 2. Private Cloud

```text
       Organization
            │
      ┌─────┴─────┐
      │ Private   │
      │   Cloud   │
      └───────────┘
```

* Used by **one organization**.
* Can be on-premises or hosted by a provider.
* Greater control and isolation.
* Generally higher cost. 

---

### 3. Community Cloud

```text
 University A ─┐
 University B ─┼──→ Community Cloud
 University C ─┘
```

Multiple organizations with **similar goals, policies, or security requirements** share the cloud.

Example:

> Five engineering colleges sharing infrastructure for digital libraries and research laboratories. 

---

### 4. Hybrid Cloud

Combines different deployment environments, especially:

```text
       Hybrid Cloud
        /         \
       ↓           ↓
 Private         Public
 Cloud           Cloud
   │               │
Sensitive       Normal/
data            scalable workload
```

Example from your PPT:

```text
Private Cloud → Student records
Public AWS    → College website
```

Both environments work together. 

---

### Service/Delivery Model

> **What are you getting?**

```text
IaaS
PaaS
SaaS
FaaS
BaaS
DBaaS
SECaaS
```

### Deployment Model

> **Where/who is the cloud environment for?**

```text
Public
Private
Community
Hybrid
```

So you can have combinations such as:

```text
AWS Public Cloud
       +
      IaaS
       ↓
EC2

AWS Public Cloud
       +
      PaaS
       ↓
App platform
```
# P7(NIST, APIs, REST, GraphQL)
# 1. NIST Cloud Computing Reference Architecture

## What is NIST?

**NIST = National Institute of Standards and Technology.**

The **NIST Cloud Computing Reference Architecture** is a **vendor-neutral conceptual model** that helps us understand the major roles and interactions in a cloud computing environment.

It is not an AWS architecture or Azure architecture. It is a general model that can be used to understand cloud systems regardless of provider. 

### Why do we need it?

Cloud environments involve several parties:

```text
Consumer
Provider
Broker
Auditor
Carrier
```

NIST gives us a common vocabulary for understanding what each party does.

---

# 2. Five NIST Cloud Actors

NIST identifies **five major performers**:

```text
1. Cloud Consumer
2. Cloud Provider
3. Cloud Broker
4. Cloud Auditor
5. Cloud Carrier
```



## Simple architecture

```text
                 Cloud Consumer
                       │
                       │
                 Cloud Carrier
                       │
                       ↓
                Cloud Provider
                  ↑          ↑
                  │          │
             Broker       Auditor
```

The relationships aren't literally this simple in every implementation, but it's a useful mental model.

---

# 3. Cloud Consumer

### What is it?

The **Cloud Consumer** is the individual or organization that **uses cloud services**.

Examples:

* A startup using AWS
* VIT using AWS for its ERP
* You using Google Drive



### Responsibilities

The consumer:

* Selects a cloud provider
* Chooses services such as IaaS/PaaS/SaaS
* Configures resources
* Monitors usage
* Pays for consumed services



### Example

```text
VIT
 ↓
Needs ERP infrastructure
 ↓
Chooses AWS
 ↓
Uses virtual machines
 ↓
Pays AWS
```

Here:

```text
VIT = Cloud Consumer
AWS = Cloud Provider
```



---

# 4. Cloud Provider

### What is it?

The **Cloud Provider** owns, manages, and delivers cloud services.

Examples:

* AWS
* Microsoft Azure
* Google Cloud
* IBM Cloud
* Oracle Cloud



The provider manages things such as:

```text
Physical infrastructure
Servers
Storage
Networking
Virtualization
Cloud software
```

It is also responsible for things such as:

* Infrastructure management
* VM provisioning
* Storage/network management
* Security
* Automatic scaling
* Availability
* Backup
* Disaster recovery



---

# 5. Internal Components of a Cloud Provider

Your PPT further divides the provider into four major sections:

```text
Cloud Provider
│
├── Service Orchestration
├── Cloud Service Management
├── Security & Privacy
└── Physical Infrastructure
```



### Service Orchestration

Coordinates multiple cloud resources automatically.

Example:

You request a VM.

```text
Request VM
   ↓
Find physical server
   ↓
Allocate CPU
   ↓
Allocate memory
   ↓
Create storage
   ↓
Install OS
   ↓
Assign IP
   ↓
Start VM
```



This is basically the provider turning your high-level request into a sequence of infrastructure operations.

---

# 6. Cloud Broker

A **Cloud Broker** is an intermediary between consumers and providers.

The broker can help:

* Select services
* Combine services
* Optimize services



The PPT gives three broker functions:

### Service Intermediation

Adds additional value to cloud services.

### Service Aggregation

Combines services from multiple providers.

```text
AWS ─────┐
         ├──→ Broker → Consumer
Azure ───┘
```

### Service Arbitrage

Selects the best provider based on factors such as:

* Cost
* Performance



---

# 7. Cloud Auditor

A **Cloud Auditor** independently evaluates cloud services.

The auditor checks:

* Security
* Compliance
* Performance
* Information-system operations



### Types of audit

| Audit                 | Checks                                   |
| --------------------- | ---------------------------------------- |
| **Security Audit**    | Firewalls, encryption, authentication    |
| **Privacy Audit**     | Protection of personal information       |
| **Performance Audit** | Availability, response time, reliability |



### Important

The auditor is **independent**.

It does not own or consume the cloud service. 

---

# 8. Cloud Carrier

The **Cloud Carrier** provides the communication channel between the consumer and provider.

Usually this is:

* ISP
* Telecommunications provider
* Network provider

Responsibilities:

* Network connectivity
* Internet communication
* Secure data transmission

Examples in your PPT:

* Airtel
* Jio
* BSNL
* AT&T
* Verizon



### Example

```text
Student
   ↓
Jio Internet
   ↓
Google Drive
```

```text
Student = Consumer
Jio     = Carrier
Google  = Provider
```

---

# 9. Complete NIST Example

Suppose VIT hosts its ERP on AWS.

```text
                    VIT
              Cloud Consumer
                    │
                    │ Request VM
                    ↓
                  Airtel
              Cloud Carrier
                    │
                    ↓
                   AWS
              Cloud Provider
                    │
          ┌─────────┴─────────┐
          ↓                   ↓
 Service Orchestration   Cloud Management
          │
          ↓
       VM + Storage
```

Then:

```text
Broker
 ↓
May help choose/combine services

Auditor
 ↓
Checks security, performance,
compliance
```

The PPT gives essentially this complete scenario. 

---

# 10. Web Services

Now we move from **who participates in cloud computing** to **how software communicates**.

Cloud computing is based on **service-oriented computing**, where applications and resources are made available as services over the Internet.

When two applications need to communicate or exchange data, they can use **Web Services**. 

## Definition

> A **Web Service** is a software system that allows different applications to communicate over a network using standard Internet protocols such as HTTP or HTTPS.

It is designed primarily for:

> **Application-to-application communication**

rather than direct human interaction. 

---

# 11. Example of a Web Service

Suppose a weather application needs weather information.

```text
Weather App
     │
     │ Request
     ↓
Weather Server
     │
     │ Weather data
     ↓
Weather App
```

Another example:

```text
Mobile Banking App
        ↓
     Bank API
        ↓
   Bank Server
```

And a cloud example:

```text
AWS Console
     ↓
AWS API
     ↓
EC2
     ↓
Create VM
```

The PPT specifically gives EC2 instance creation as an example of API/Web Service communication. 

---

# 12. Characteristics of Web Services

| Characteristic                 | Meaning                                            |
| ------------------------------ | -------------------------------------------------- |
| **Platform Independent**       | Works across different operating systems           |
| **Language Independent**       | Java, Python, JavaScript, etc. can communicate     |
| **Internet Accessible**        | Accessible over networks                           |
| **Standardized Communication** | Uses standard protocols/data formats               |
| **Interoperable**              | Different systems can communicate                  |
| **Reusable**                   | Multiple applications can use the service          |
| **Loosely Coupled**            | Client and server remain relatively independent    |
| **Self-Contained**             | Service performs a specific function independently |



---

# 13. Components of a Web Service

Your PPT identifies three:

### 1. Service Provider

The application providing the service.

Example:

```text
Google Maps Server
```

### 2. Service Consumer

The application requesting the service.

Example:

```text
Uber App
```

### 3. Communication Protocol

The mechanism used for communication.

Usually:

```text
HTTP
HTTPS
```



So:

```text
Service Consumer
       │
       │ HTTP / HTTPS
       ↓
Service Provider
```

---

# 14. Types of Web Services

Your PPT covers **two main types**:

```text
Web Services
│
├── SOAP
└── REST
```



---

# 15. SOAP

**SOAP = Simple Object Access Protocol**

SOAP is:

* XML-based
* Highly secure
* Supports transactions
* More complex

Suitable for:

* Banking
* Healthcare
* Enterprise applications



Conceptually:

```text
Client
  ↓
SOAP Message
  ↓
SOAP Web Service
  ↓
Server
```

SOAP messages traditionally use XML.

The major tradeoff:

```text
More formal / feature-rich
          ↓
More complexity
```

---

# 16. REST

**REST = Representational State Transfer.**

REST is an **architectural style** for designing web services.

It is:

* **Not a protocol**
* **Not a programming language**

It is a set of design principles/rules. 

The PPT explains that REST became popular because compared with traditional SOAP-style communication, it can make communication lighter and simpler, with smaller messages and easier development. 

---

# 17. How REST Works

The basic flow in your PPT is:

```text
Client
  │
  │ HTTP Request
  ↓
REST API Server
  │
  ↓
Database
  │
  ↓
REST API Server
  │
  │ HTTP Response
  ↓
Client
```



Let's make that concrete.

Suppose:

```text
GET /users/42
```

The request reaches the REST API.

```text
Client
  ↓
GET /users/42
  ↓
Router
  ↓
Business Logic
  ↓
Database
  ↓
User 42
  ↓
JSON Response
  ↓
Client
```

---

# 18. REST Resources

REST is centered around **resources**.

Examples:

```text
/users
/products
/orders
/courses
/payments
```

Specific resources:

```text
/users/42
/products/100
/orders/500
```

Think:

```text
/users
```

means a collection.

```text
/users/42
```

means one specific user.

---

# 19. HTTP Methods in REST

This is where REST becomes useful in actual backend engineering.

| Method     | Meaning          | Example            |
| ---------- | ---------------- | ------------------ |
| **GET**    | Read/retrieve    | `GET /users/42`    |
| **POST**   | Create           | `POST /users`      |
| **PUT**    | Replace resource | `PUT /users/42`    |
| **PATCH**  | Partially modify | `PATCH /users/42`  |
| **DELETE** | Delete           | `DELETE /users/42` |

### GET

```http
GET /users/42
```

> Give me user 42.

### POST

```http
POST /users
```

```json
{
  "name": "Rehaan",
  "email": "rehaan@example.com"
}
```

> Create a new user.

### PUT

```http
PUT /users/42
```

> Replace user 42 with this representation.

### PATCH

```http
PATCH /users/42
```

```json
{
  "email": "new@example.com"
}
```

> Change only this part of user 42.

### DELETE

```http
DELETE /users/42
```

> Delete user 42.

---

# 20. PUT vs PATCH

This deserves its own note.

### PUT

Think:

> **Replace**

```text
Old User
A B C D

PUT

New User
A X Y Z
```

You are providing the new representation.

### PATCH

Think:

> **Modify**

```text
Old User
A B C D

PATCH:
change B → X

Result
A X C D
```

So:

```text
PUT   = replacement
PATCH = partial modification
```

---

# 21. REST and Representations

The word **"Representational"** in REST matters.

The server has a resource:

```text
User #42
```

The client doesn't receive the database object itself.

It receives a **representation** of it.

For example:

```json
{
  "id": 42,
  "name": "Rehaan"
}
```

JSON is common.

XML can also be used.

Your PPT explicitly says REST supports multiple data formats. 

---

# 22. Statelessness

A major REST principle is **statelessness**.

Each request should contain the information needed to process it.

Example:

```http
GET /users/42
Authorization: Bearer <token>
```

The server shouldn't need to remember:

```text
Request #1:
"This is Rehaan."

Request #2:
"Remember me."

Request #3:
"Now give me user 42."
```

Instead:

```text
Request
   ↓
Contains necessary context
   ↓
Server processes it
   ↓
Response
```

This is particularly useful for cloud systems because multiple API instances can handle requests:

```text
             Load Balancer
             /     |     \
            ↓      ↓      ↓
          API-1  API-2  API-3
```

Any instance can process the request.

---

# 23. REST API Example in Node.js

Since you're using Node.js, here's the concrete version.

```js
import express from "express";

const app = express();

app.use(express.json());

const users = [
    { id: 1, name: "Alice" },
    { id: 2, name: "Bob" }
];

app.get("/users/:id", (req, res) => {
    const id = Number(req.params.id);

    const user = users.find(user => user.id === id);

    if (!user) {
        return res.status(404).json({
            error: "USER_NOT_FOUND"
        });
    }

    res.json(user);
});

app.post("/users", (req, res) => {
    const user = {
        id: users.length + 1,
        name: req.body.name
    };

    users.push(user);

    res.status(201).json(user);
});

app.patch("/users/:id", (req, res) => {
    const id = Number(req.params.id);

    const user = users.find(user => user.id === id);

    if (!user) {
        return res.status(404).json({
            error: "USER_NOT_FOUND"
        });
    }

    if (req.body.name !== undefined) {
        user.name = req.body.name;
    }

    res.json(user);
});

app.delete("/users/:id", (req, res) => {
    const id = Number(req.params.id);

    const index = users.findIndex(user => user.id === id);

    if (index === -1) {
        return res.status(404).json({
            error: "USER_NOT_FOUND"
        });
    }

    users.splice(index, 1);

    res.status(204).send();
});

app.listen(3000);
```

The important engineering flow is:

```text
HTTP Request
     ↓
Router
     ↓
Controller
     ↓
Business Logic
     ↓
Database
     ↓
HTTP Response
```

The Express syntax is merely the implementation detail.

---

# 24. API

**API = Application Programming Interface.**

Your PPT defines an API as a set of rules allowing one software application to communicate with another. 

An API defines things such as:

* Available operations
* Input parameters
* Output format
* Communication methods

---

# 25. Why APIs?

APIs enable:

### Data exchange

```text
Frontend ↔ Backend
```

### Service integration

```text
Your App
   ↓
Google Maps API
```

### Automation

```text
Script
  ↓
Cloud API
  ↓
Create resource
```

### Cloud management

```text
Your Code
   ↓
AWS API
   ↓
Create EC2 instance
```

Your PPT explicitly identifies **data exchange, service integration, automation, and cloud management** as API uses. 

---

# 26. API vs Web Service vs REST

This is the distinction you absolutely want in your notes.

| Concept         | What it is                                                    |
| --------------- | ------------------------------------------------------------- |
| **API**         | Interface/contract for software communication                 |
| **Web Service** | Network-accessible service enabling application communication |
| **REST**        | Architectural style for designing web services/APIs           |
| **HTTP**        | Communication protocol                                        |
| **JSON**        | Data representation format                                    |
| **SOAP**        | XML-based web-service protocol                                |

Think:

```text
             API
              │
        Interface/Contract
              │
        ┌─────┴─────┐
        ↓           ↓
       REST        SOAP
        │
        ↓
       HTTP
        │
        ↓
       JSON
```

More accurately, JSON isn't required by REST, and REST isn't the only way to build an API. The diagram is showing a **common implementation**, not a dependency chain.

---

# 27. REST vs SOAP

| REST                        | SOAP                                |
| --------------------------- | ----------------------------------- |
| Architectural style         | Protocol                            |
| Lightweight                 | More formal/heavy                   |
| Commonly uses HTTP          | Can operate over several protocols  |
| JSON/XML/etc.               | XML                                 |
| Simple development          | More complex                        |
| Common in modern cloud APIs | Common in enterprise/legacy systems |
| Uses HTTP methods           | Uses SOAP operations/messages       |

Your PPT specifically emphasizes SOAP's XML basis, security, transaction support and complexity, while REST is presented as lightweight, fast and simple. 

---

# 28. REST vs GraphQL

**GraphQL is not covered in your PPT section**, so treat this as additional engineering knowledge, not something to attribute to your lecturer.

### REST

Server exposes resources:

```http
GET /users/42
GET /users/42/orders
```

The server generally determines the response structure.

### GraphQL

Usually:

```http
POST /graphql
```

The client asks for exactly what it needs:

```graphql
query {
  user(id: 42) {
    name
    email
  }
}
```

Response:

```json
{
  "data": {
    "user": {
      "name": "Rehaan",
      "email": "rehaan@example.com"
    }
  }
}
```

### Core difference

```text
REST
Client asks:
"Give me this resource."

GraphQL
Client asks:
"Give me these exact fields."
```

---

# 29. Other API Approaches

Again, these go beyond the PPT, but they're worth knowing as an engineer.

| Approach      | Main idea                           | Common use                         |
| ------------- | ----------------------------------- | ---------------------------------- |
| **REST**      | Resources over HTTP                 | Public web APIs                    |
| **GraphQL**   | Client-defined queries              | Complex frontend data requirements |
| **gRPC**      | Remote procedure calls              | Internal microservices             |
| **SOAP**      | XML-based protocol                  | Enterprise integrations            |
| **WebSocket** | Persistent bidirectional connection | Real-time apps                     |
| **Webhook**   | Server sends event notification     | Payment/event integrations         |

Don't call all of these "REST alternatives." They solve somewhat different problems.

---

# 30. Complete Mental Model

This is the part to keep at the **bottom of your notes**.

```text
                     CLOUD COMPUTING
                            │
                            ↓
                  Service-Oriented Model
                            │
                            ↓
                      Web Services
                            │
                 ┌──────────┴──────────┐
                 ↓                     ↓
               SOAP                   REST
                 │                     │
           XML-based              Architectural
           protocol                  style
                                       │
                                       ↓
                                      HTTP
                                       │
                                       ↓
                                    REST API
                                       │
                          ┌────────────┴────────────┐
                          ↓                         ↓
                       Request                  Response
                          │                         │
                          ↓                         ↑
                       Backend ───────────────→ Database
```

And above that sits the **NIST cloud ecosystem**:

```text
                     NIST
                      │
       ┌──────────────┼──────────────┐
       ↓              ↓              ↓
 Consumer          Provider        Carrier
                      │
                ┌─────┴─────┐
                ↓           ↓
             Broker       Auditor
```

---
