## Infra Vs Arch
### 🏗️ Infrastructure
- **Definition**: The *foundation* — the hardware, cloud services, networking, and storage that support AI systems.
- **Focus**: Ensuring scalability, reliability, and performance.
- **Examples**:
  - GPUs/TPUs for training deep learning models
  - Cloud platforms (Azure, AWS, GCP)
  - Data pipelines and storage systems
  - Kubernetes clusters for deployment
- **AI Engineer’s role**: Setting up and maintaining the environment where models are trained, tested, and deployed.

### 🧩 Architecture
- **Definition**: The *design* — how AI models, algorithms, and components are structured and interact.
- **Focus**: Optimizing the logic, flow, and efficiency of AI solutions.
- **Examples**:
  - Neural network design (CNNs, RNNs, Transformers)
  - Model serving architecture (microservices, APIs)
  - Workflow orchestration (how data flows through preprocessing → training → inference)
- **AI Engineer’s role**: Designing the model structure, choosing the right algorithms, and ensuring the system integrates smoothly with applications.

### ⚖️ Key Difference
- **Infrastructure = the “where”** → the physical and cloud systems that make AI possible.  
- **Architecture = the “how”** → the conceptual and technical design of AI models and systems.  

Think of it like building a house:
- Infrastructure = the land, electricity, plumbing.  
- Architecture = the blueprint, layout, and design of the house.  

## Sys Designer Vs Sys Arch! 
### 🏗️ System Designer
- **Role**: Focuses on *conceptual design* of a system.
- **Scope**:
  - Defines modules, data flow, and interactions.
  - Creates diagrams and specifications.
  - Ensures usability, maintainability, and scalability.
- **Output**: A **blueprint** or design document that guides implementation.

### 🧩 System Architect
- **Role**: Focuses on *high-level structure and standards* of a system.
- **Scope**:
  - Chooses frameworks, platforms, and technologies.
  - Defines architectural patterns (e.g., microservices, layered architecture).
  - Ensures compliance with performance, security, and reliability requirements.
- **Output**: An **architecture plan** that governs how the system is built and evolves.

### ⚖️ Key Difference
- **System Designer** → More about *designing the "what"* (functional layout, user needs, workflows).  
- **System Architect** → More about *designing the "how"* (technical structure, integration, standards).  

👉 Think of it like building a city:
- The **designer** plans where roads, parks, and buildings go.  
- The **architect** decides how those buildings are structurally built, what materials are used, and how utilities connect.  

## Clustering(Means linking multiple servers as a single server, done through kubernetes)