Virtual Machine, Its a isolated environment created on top of a existing hardware using hypervisor.
Virtualization, Running multiple virtual machines(Containers) over a single hardware.
Host Machine, The physical machine over which the vms run.
Guest Machine, The Virtual Machine is called as Guest Machine.
Hypervisor, The software that is on top of OS that creates and manages VMs.
Containers, Lightweight VMs(Just libraries, Packages, Codebase)
Image, A pre-configured file which contains all information(OS, Network Config, Packages, libraries, environments, applications) in order to install a Virtual Server.

Virtualization
    Virtualization as a broad term(Virtual Memory, Virtual Storage, Virtual CPU)

        Virtual Containers(Containers are lightweight packages that run applications with shared host OS resources, while virtual containers (virtual machines) emulate an entire computer with its own operating system.)
            |
        Virtualization layer(hypervisor, OS)
            |
        Hardware

    Server Deployment issues
        Virtualizing x86 platforms means running multiple **virtual machines(Each VM can have own OS)** on a single physical server using a hypervisor.  

        👉 This boosts server utilization, reduces hardware and management costs, and provides better failover and disaster recovery by allowing workloads to be isolated, moved, and scaled more efficiently.

    Features/Characteristics of Virtualization
        1. Partitioning(CPU, RAM, Storage allocation)
        2. Encapsulation of data(Storing complete environment as a file. Ubuntu.vmx)
        3. Isolation(Each VM gets its own isolated environment with much Data security)
        4. Hardware Independence
        5. Resources Sharing
        6. High availibility

    Virtualization Software(Hypervisor, VMWare, Docker, Kubernetes)
    Virtual PC/Network

    Benefits of Virtualization(Cost reduction, Hardware less needed, Less power, Better reliability, Easy Scalibility, Flexible, Many OS Runs, Many Application on same server, Easy Deployment, Easy Security)

Virtual Server(Same as VM)
    A software based server or application that behaves like a physical server.
    It has its own OS, Network, Application

    Why Virtual Server? --> Look above at line 17!

    VS has its own vCPU, vRAM, vHard Disck, vNetwork Interface Card, Installed Guest OS and applications.

    VS Provisioning
        Creating a VS is called as Provisioning. User creates a VS(With proper iso file, hardware allocation), then hypervisor does its work(Allocates Hardware), then VM Image is created, 
Virtual Infrastructure Manager(VIM)
    When a Physical server has Multiple VMs, we need to manage them.This is the task of VIM.(VMWare vService, Kubernetes)

Q. Virtual Hardware Vs Physical Hardware(Real,Multiple OS, Partitioning, Isolation, Encapsulation, Physical server utilization less, scalibility less)

Hypervisor
    Hardware --> Hypervisor --> OS --> VMs
    Types:
        Type 1:
            Installed directly on a OS. No need to fight with OS, complete control over hardware

            Used in enterprise hardware

            Highly efficient(As complete control over hardware, kernel level)

        Type 2:
            A Hypervisor runs on Hosted OS.(VMWare workstation)

Ring Modes in virtualization
    Processors (such as x86 CPUs) provide 
    protection rings to prevent user programs from 
    directly accessing hardware or performing 
    critical system operations

    Ring 0 Highest Kernel level access
    Ring 1 medium-high Drivers access
    Ring 2 medium-low Middleware level access
    Ring 3 lowest Application level access

Q. Problems in Virtualization
    If I wanna run 3 OS at same time, but each OSs kernel expect Ring 0 level access but this creates conflict
    Solution is that Hypervisor controls Ring 0

     Ring Modes in Type 1 Hypervisor
        Ring 3 
            • Applications -> Guest Operating System -> hypervisor -> hardware
        Ring 0 
            • GOS -> Hypervisor -> Hardware
        
        • The hypervisor occupies the highest privilege level and manages 
        CPU, memory, storage, and devices. Guest operating systems run 
        with restricted privileges, even though they believe they are 
        executing as the operating system.

Privileged Instructions
    • A privileged instruction is a CPU instruction that can only be 
    executed in Ring 0 because it affects hardware or critical system 
    resources.
    • If a program running outside Ring 0 attempts to execute one, the 
    processor generates an exception (trap).
    Examples of Privileged Instructions
        • Enable or disable interrupts 
        • Access I/O ports 
        • Modify page tables 
        • Change CPU control registers 
        • Halt the processor 
        • Configure hardware devices

        Role of Privileged Instructions in Virtualization
            • Suppose a guest operating system wants to access the 
            hard disk.
            • Normally, it executes a privileged instruction.
            Guest OS -> Execute privileged instruction ->CPU detects 
            restricted execution -> Trap (Exception) -> Hypervisor gains 
            control -> Hypervisor checks request ->Performs or 
            emulates operation -> Returns control to Guest OS
            • The guest operating system thinks it has accessed the 
            hardware directly, but the hypervisor has actually managed
            the operation safely

Types of virtualization
    How?
        Full 
        Para
    What?
        OS Desktop Network Application Storage or Server

    1. Full
    • In Full Virtualization, the hypervisor provides a complete 
    simulation of the underlying hardware. The guest operating 
    system runs without any modifications, believing it has 
    exclusive access to the physical machine.
        Arch:
            Application -> GOS -> Hypervisor -> Hardware
        Binary translation:
            • Full virtualization leverages binary translation to enable
            unmodified operating systems to run in virtual machines, binary translation helps to convert machine instruction of GOS run on HOS.
        Characteristics:
            All from line 23.
            And Hypervisor handles priviledged Instructions
            Can run unmodified OS like windows
            More compatible
            Easy deployment
        Issue:
            Heating
            Power consumption
            More Virtualization overhead
    2. Para
    • In Paravirtualization, the guest operating system is modified to
    communicate directly with the hypervisor using special interfaces called
    hypercalls instead of executing sensitive hardware operations directly.
    • A hypercall is similar to a system call, which is how a program asks the
    operating system to do something (like read a file or allocate memory).

    Architecture
        • Applications -> Modified Guest OS(With Hypercall) -> Hypervisor -> Physical Hardware

    Why Hypercalls Are Needed
        • In full virtualization, the guest OS doesn’t know it’s virtualized, so
        the hypervisor must trick or emulate hardware for it.So, this requires Binary translations(Dynamic or static)
        • In para-virtualization, the guest OS is modified to be aware that it’s
        running in a virtualized environment.
        • So instead of trying to use privileged CPU instructions (which it
        can't use directly), the guest OS makes a hypercall to ask the
        hypervisor to perform the action safely.

        Characteristics:
            GOS must be modified(Aware of its virtualized environment)
            Less Virtualization overhead(Virtualization overhead refers to the extra computing cost (CPU cycles, memory usage, I/O delays) that occurs when workloads run inside virtual machines instead of directly on physical hardware. On modern systems, this overhead is usually low (2–10%), but it can rise significantly depending on workload type and configuration.)
            Less compatible
            Complex Deployment

    3. Desktop Virtualization(Run your PC, but remotely)
        • Desktop Virtualization is a technology that separates the desktop
        environment (the user’s interface, applications, and data) from the
        physical computer.
        Instead of the desktop running on your local machine, it runs on a
        central server or virtual machine (VM) and you just connect to it
        remotely.
        
        Creation process:
            Create a Virtual server
            Host the server
            As per user request, divide the server resources

        Types:
            Virtual Desktop Infrastructure(Deploy your own DV Server)
            DaaS(desktop as a Service)(Here a 3rd party deploys)
            Local host
            Hosted remotely
        Challenges:
            Initial cost hefty, managing hardware, security, scalibility
    4. Server Virtualization(Entire virtualization)
        Challenges(Licensing, unused VMs aern't cleared automatically)
    5. OS Virtualization(Containerization)
        Terminologies(Namespaces[User Ids, ...],container, union file system, Control groups(CPU,....))
    6. Storage Virtualizations
        Types:
            Block-level, file-level, array-based
    7. Network Virtualization
        Network Virtualization creates multiple logical networks over a 
        shared physical network infrastructure.

VM Cloning
    Process of creating a copy of a VM through respective Image.
    Need?
        Reduces Redundant task.
        Easy deployment
        No manually create a VM
        Used in case for scalibility of a application

    Types?
        Full(Completely Isolated Environment for this VMs)
        Linked(Shares the allocated resources of the Parent VM)

Cloud storage Device
    Traditional Vs Cloud(Recovery, unlimited, cost, remote access, security, isolation)
Cloud storage level
    Files – Collections of data are grouped into files that are
    located in folders.
    Blocks – The lowest level of storage and the closest to the
    hardware, a block is the smallest unit of data that is still
    individually accessible.
    Datasets – Sets of data are organized into a table-based,
    delimited, (separated ), or record format.
    Objects – Data and its associated metadata (information
    about the data) are organized as Web-based resources.
Cloud usage monitor
    Agents - polling, resources, monitoring
