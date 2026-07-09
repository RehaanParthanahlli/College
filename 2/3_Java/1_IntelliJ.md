# IntelliJ IDEA Setup Guide for Java Development

## Step 1: Download & Install

1. Go to [jetbrains.com/idea/download](https://www.jetbrains.com/idea/download/)
2. Choose your edition:
   - **Community Edition** — Free, sufficient for pure Java, Maven, Gradle, Android
   - **Ultimate Edition** — Paid, adds Spring, Jakarta EE, database tools, remote dev (30-day free trial)
3. Download the **Windows `.exe` installer**
4. Run the installer and follow the wizard:
   - **Installation Options** (check these):
     - ✅ 64-bit launcher
     - ✅ Add "Open Folder as Project" to context menu
     - ✅ Add to PATH
     - ✅ .java / .groovy / .kt file associations (optional)
   - Choose a start menu folder → **Install**

---

## Step 2: First Launch & Initial Configuration

1. Launch IntelliJ IDEA
2. **Import Settings** — Select "Do not import settings" (fresh install)
3. **UI Theme** — Darcula (dark) or Light
4. **Plugins** — You can skip or install later. For Java, the defaults are sufficient.
5. **License** — Activate Ultimate with license or start Community

---

## Step 3: Configure the JDK

IntelliJ needs to know where your JDK is installed.

1. Go to **File → Project Structure** (or `Ctrl + Alt + Shift + S`)
2. Under **Project Settings → Project**:
   - **SDK**: Click dropdown → **Add SDK → JDK**
   - Browse to your JDK installation path (e.g., `C:\Program Files\Java\jdk-21`)
   - Click **OK**
3. Set **Language level** to match your JDK (e.g., `21 — Local variable syntax for lambda parameters`)

> **Tip:** If you already set `JAVA_HOME`, IntelliJ may auto-detect it.

---

## Step 4: Create Your First Java Project

### Method A: New Project (Recommended for Beginners)

1. **File → New → Project**
2. Select **Java** on the left
3. Ensure your JDK is selected in the dropdown
4. **Uncheck** "Add sample code" (we'll write our own) or leave checked
5. Click **Create**

### Method B: New Project from Scratch

1. **File → New → Project → Empty Project**
2. Name it (e.g., `HelloWorldProject`) and choose location
3. Once created, right-click project root → **New → Module → Java**
4. Select JDK → **Next → Finish**

---

## Step 5: Create & Run Hello World

1. In the **Project** tool window (left), expand your project
2. Right-click `src` folder → **New → Java Class**
3. Name it `HelloWorld` and press Enter
4. Type the code:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

5. **Run it** — Click the green ▶️ arrow next to `main()`, or press `Ctrl + Shift + F10`

**Output appears in the Run console at the bottom:**
```
Hello, World!

Process finished with exit code 0
```

---

## Step 6: Essential IntelliJ Shortcuts

| Action | Shortcut |
|--------|----------|
| Run current file | `Shift + F10` |
| Debug | `Shift + F9` |
| Auto-complete | `Ctrl + Space` |
| Smart complete | `Ctrl + Shift + Space` |
| Reformat code | `Ctrl + Alt + L` |
| Generate code (getters, constructors) | `Alt + Insert` |
| Find class/file | `Ctrl + N` / `Ctrl + Shift + N` |
| Refactor rename | `Shift + F6` |
| Go to definition | `Ctrl + B` |
| Comment/uncomment line | `Ctrl + /` |
| Search everywhere | `Shift` (double tap) |

---

## Step 7: Useful Settings to Configure

Go to **File → Settings** (`Ctrl + Alt + S`):

| Setting Path | Recommendation |
|-------------|----------------|
| **Editor → Font** | Increase size (e.g., 14–16) |
| **Editor → General → Auto Import** | Check "Add unambiguous imports on the fly" |
| **Build → Build Tools → Maven/Gradle** | Configure if using build tools |
| **Appearance & Behavior → System Settings** | Disable "Reopen projects on startup" if preferred |
| **Plugins** | Install: **.ignore**, **Rainbow Brackets**, **Key Promoter X** |

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| "No JDK specified" | File → Project Structure → SDK → Add JDK |
| "Cannot resolve symbol 'String'" | SDK not configured — re-add JDK |
| Run button grayed out | Right-click `src` → Mark Directory as → Sources Root |
| Terminal shows wrong Java version | File → Settings → Tools → Terminal → Override PATH with project JDK |
| Slow performance | Help → Edit Custom VM Options → increase `-Xms` and `-Xmx` (e.g., `-Xmx2048m`) |

---

## Next Steps

Once comfortable:
- **Build tools**: File → New → Project → Maven / Gradle
- **Version control**: VCS → Enable Version Control Integration → Git
- **Spring Boot**: Ultimate has built-in Spring Initializr; Community can use [start.spring.io](https://start.spring.io) and import as Maven project