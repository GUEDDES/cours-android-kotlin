# Comparaison Complète Java vs Kotlin pour Android

## Table des Matières
1. [Syntaxe de Base](#syntaxe-de-base)
2. [Variables et Types](#variables-et-types)
3. [Fonctions](#fonctions)
4. [Classes et Objets](#classes-et-objets)
5. [Null Safety](#null-safety)
6. [Collections](#collections)
7. [Lambdas et Fonctions d'Ordre Supérieur](#lambdas-et-fonctions-dordre-supérieur)
8. [Extensions](#extensions)
9. [Android Spécifique](#android-spécifique)
10. [Asynchrone](#asynchrone)

---

## Syntaxe de Base

### Variables

#### Java
```java
// Variable mutable
String name = "Android";
int age = 10;

// Variable immutable (constante)
final String API_KEY = "abc123";
final int MAX_SIZE = 100;
```

#### Kotlin
```kotlin
// Variable mutable
var name: String = "Android"
var age = 10  // Type inference

// Variable immutable
val apiKey = "abc123"
val maxSize = 100
```

### Commentaires

#### Java
```java
// Commentaire sur une ligne

/* Commentaire
   sur plusieurs
   lignes */

/**
 * Documentation JavaDoc
 * @param param Description
 * @return Description
 */
```

#### Kotlin
```kotlin
// Commentaire sur une ligne

/* Commentaire
   sur plusieurs
   lignes */

/**
 * Documentation KDoc
 * @param param Description
 * @return Description
 */
```

---

## Variables et Types

### Types Primitifs

#### Java
```java
byte b = 1;
short s = 10;
int i = 100;
long l = 1000L;
float f = 10.5f;
double d = 10.5;
char c = 'A';
boolean bool = true;
```

#### Kotlin
```kotlin
val b: Byte = 1
val s: Short = 10
val i: Int = 100
val l: Long = 1000L
val f: Float = 10.5f
val d: Double = 10.5
val c: Char = 'A'
val bool: Boolean = true
```

### Conversion de Types

#### Java
```java
int i = 100;
long l = i;  // Conversion implicite
double d = i;  // Conversion implicite

String str = "123";
int num = Integer.parseInt(str);
```

#### Kotlin
```kotlin
val i: Int = 100
val l: Long = i.toLong()  // Conversion explicite
val d: Double = i.toDouble()  // Conversion explicite

val str = "123"
val num = str.toInt()
```

---

## Fonctions

### Déclaration

#### Java
```java
public int add(int a, int b) {
    return a + b;
}

public void printMessage(String message) {
    System.out.println(message);
}

// Avec valeur par défaut (nécessite surcharge)
public void greet() {
    greet("Hello");
}

public void greet(String message) {
    System.out.println(message);
}
```

#### Kotlin
```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}

// Expression function
fun add(a: Int, b: Int) = a + b

fun printMessage(message: String) {
    println(message)
}

// Avec valeur par défaut
fun greet(message: String = "Hello") {
    println(message)
}

// Appels nommés
greet(message = "Bonjour")
```

### Fonctions Variadiques

#### Java
```java
public int sum(int... numbers) {
    int total = 0;
    for (int num : numbers) {
        total += num;
    }
    return total;
}

int result = sum(1, 2, 3, 4, 5);
```

#### Kotlin
```kotlin
fun sum(vararg numbers: Int): Int {
    return numbers.sum()
}

val result = sum(1, 2, 3, 4, 5)

// Spread operator
val nums = intArrayOf(1, 2, 3)
val result2 = sum(*nums)
```

---

## Classes et Objets

### Classe Simple

#### Java
```java
public class Person {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
```

#### Kotlin
```kotlin
// Classe simple
class Person(var name: String, var age: Int) {
    override fun toString(): String {
        return "Person(name='$name', age=$age)"
    }
}

// Data class (génère automatiquement getters, setters, toString, equals, hashCode, copy)
data class Person(var name: String, var age: Int)
```

### Propriétés

#### Java
```java
public class User {
    private String email;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        }
    }
}
```

#### Kotlin
```kotlin
class User {
    var email: String = ""
        get() = field
        set(value) {
            if (value.contains("@")) {
                field = value
            }
        }
}

// Ou plus simple:
class User {
    var email: String = ""
        set(value) {
            field = if (value.contains("@")) value else field
        }
}
```

### Héritage

#### Java
```java
public class Animal {
    private String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("Some sound");
    }
}

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}
```

#### Kotlin
```kotlin
// Les classes sont final par défaut
open class Animal(val name: String) {
    open fun makeSound() {
        println("Some sound")
    }
}

class Dog(name: String) : Animal(name) {
    override fun makeSound() {
        println("Woof!")
    }
}
```

### Classes Abstraites et Interfaces

#### Java
```java
public abstract class Shape {
    abstract double area();
    
    public void display() {
        System.out.println("Area: " + area());
    }
}

public interface Drawable {
    void draw();
    
    default void clear() {
        System.out.println("Clearing...");
    }
}

public class Circle extends Shape implements Drawable {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    double area() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }
}
```

#### Kotlin
```kotlin
abstract class Shape {
    abstract fun area(): Double
    
    fun display() {
        println("Area: ${area()}")
    }
}

interface Drawable {
    fun draw()
    
    fun clear() {
        println("Clearing...")
    }
}

class Circle(private val radius: Double) : Shape(), Drawable {
    override fun area() = Math.PI * radius * radius
    
    override fun draw() {
        println("Drawing circle")
    }
}
```

### Companion Object (Static)

#### Java
```java
public class MathUtils {
    public static final double PI = 3.14159;
    
    public static int add(int a, int b) {
        return a + b;
    }
}

// Utilisation
int result = MathUtils.add(5, 3);
```

#### Kotlin
```kotlin
class MathUtils {
    companion object {
        const val PI = 3.14159
        
        fun add(a: Int, b: Int) = a + b
    }
}

// Utilisation
val result = MathUtils.add(5, 3)

// Ou objet singleton
object MathUtils {
    const val PI = 3.14159
    fun add(a: Int, b: Int) = a + b
}
```

---

## Null Safety

### Gestion du Null

#### Java
```java
String name = null;

// Risque de NullPointerException
int length = name.length();

// Protection manuelle
if (name != null) {
    int length = name.length();
}

// Avec Optional (Java 8+)
Optional<String> optName = Optional.ofNullable(name);
int length = optName.map(String::length).orElse(0);
```

#### Kotlin
```kotlin
// Non-nullable par défaut
var name: String = "Kotlin"
// name = null  // Erreur de compilation

// Nullable explicite
var name: String? = null

// Safe call operator
val length = name?.length  // Retourne null si name est null

// Elvis operator
val length = name?.length ?: 0  // 0 si name est null

// Not-null assertion (à éviter)
val length = name!!.length  // Lance une exception si name est null

// let function
name?.let {
    println("Name: $it")
    println("Length: ${it.length}")
}
```

### Smart Cast

#### Java
```java
Object obj = "Hello";

if (obj instanceof String) {
    String str = (String) obj;  // Cast explicite
    int length = str.length();
}
```

#### Kotlin
```kotlin
val obj: Any = "Hello"

if (obj is String) {
    // Pas besoin de cast, smart cast automatique
    val length = obj.length
}

// Avec safe cast
val str: String? = obj as? String
```

---

## Collections

### Listes

#### Java
```java
// Liste mutable
List<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");

// Liste immutable (Java 9+)
List<String> immutableNames = List.of("Alice", "Bob");

// Iteration
for (String name : names) {
    System.out.println(name);
}

// Stream API (Java 8+)
names.stream()
    .filter(name -> name.startsWith("A"))
    .forEach(System.out::println);
```

#### Kotlin
```kotlin
// Liste mutable
val names = mutableListOf<String>()
names.add("Alice")
names.add("Bob")

// Liste immutable
val immutableNames = listOf("Alice", "Bob")

// Iteration
for (name in names) {
    println(name)
}

names.forEach { name ->
    println(name)
}

// Opérations fonctionnelles
names.filter { it.startsWith("A") }
    .forEach { println(it) }

// Map, filter, reduce
val lengths = names.map { it.length }
val filtered = names.filter { it.length > 3 }
val total = names.map { it.length }.sum()
```

### Maps

#### Java
```java
// Map mutable
Map<String, Integer> ages = new HashMap<>();
ages.put("Alice", 25);
ages.put("Bob", 30);

// Map immutable (Java 9+)
Map<String, Integer> immutableAges = Map.of(
    "Alice", 25,
    "Bob", 30
);

// Iteration
for (Map.Entry<String, Integer> entry : ages.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

#### Kotlin
```kotlin
// Map mutable
val ages = mutableMapOf<String, Int>()
ages["Alice"] = 25
ages["Bob"] = 30

// Map immutable
val immutableAges = mapOf(
    "Alice" to 25,
    "Bob" to 30
)

// Iteration
for ((name, age) in ages) {
    println("$name: $age")
}

ages.forEach { (name, age) ->
    println("$name: $age")
}
```

---

## Lambdas et Fonctions d'Ordre Supérieur

### Lambdas

#### Java
```java
// Interface fonctionnelle
@FunctionalInterface
interface Operation {
    int apply(int a, int b);
}

Operation add = (a, b) -> a + b;
int result = add.apply(5, 3);

// Avec Button
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Action
    }
});

// Ou avec lambda (Java 8+)
button.setOnClickListener(v -> {
    // Action
});
```

#### Kotlin
```kotlin
// Type de fonction
val add: (Int, Int) -> Int = { a, b -> a + b }
val result = add(5, 3)

// Avec Button
button.setOnClickListener { view ->
    // Action
}

// Si un seul paramètre, utilisable comme 'it'
button.setOnClickListener {
    // 'it' représente le view
}
```

### Fonctions d'Ordre Supérieur

#### Java
```java
public int operate(int a, int b, Operation op) {
    return op.apply(a, b);
}

int result = operate(5, 3, (a, b) -> a + b);
```

#### Kotlin
```kotlin
fun operate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

val result = operate(5, 3) { a, b -> a + b }

// Fonctions intégrées
val numbers = listOf(1, 2, 3, 4, 5)
val doubled = numbers.map { it * 2 }
val evens = numbers.filter { it % 2 == 0 }
val sum = numbers.reduce { acc, num -> acc + num }
```

---

## Extensions

### Fonctions d'Extension

#### Java
```java
// Impossible directement, nécessite une classe utilitaire
public class StringUtils {
    public static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }
}

// Utilisation
boolean result = StringUtils.isPalindrome("radar");
```

#### Kotlin
```kotlin
// Extension function
fun String.isPalindrome(): Boolean {
    return this == this.reversed()
}

// Utilisation
val result = "radar".isPalindrome()

// Extensions Android courantes
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

// Utilisation
showToast("Hello")
textView.visible()
progressBar.gone()
```

---

## Android Spécifique

### Activity

#### Java
```java
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Clicked");
            }
        });
    }
}
```

#### Kotlin
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var button: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        
        button.setOnClickListener {
            textView.text = "Clicked"
        }
    }
}

// Avec View Binding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.button.setOnClickListener {
            binding.textView.text = "Clicked"
        }
    }
}
```

### Intent

#### Java
```java
// Démarrer une Activity
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("KEY", "value");
intent.putExtra("NUMBER", 42);
startActivity(intent);

// Récupérer les extras
Intent intent = getIntent();
String value = intent.getStringExtra("KEY");
int number = intent.getIntExtra("NUMBER", 0);
```

#### Kotlin
```kotlin
// Démarrer une Activity
val intent = Intent(this, SecondActivity::class.java).apply {
    putExtra("KEY", "value")
    putExtra("NUMBER", 42)
}
startActivity(intent)

// Récupérer les extras
val value = intent.getStringExtra("KEY")
val number = intent.getIntExtra("NUMBER", 0)

// Avec extension
inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.block()
    startActivity(intent)
}

// Utilisation
startActivity<SecondActivity> {
    putExtra("KEY", "value")
    putExtra("NUMBER", 42)
}
```

### RecyclerView Adapter

#### Java
```java
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> items;
    private OnItemClickListener listener;
    
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }
    
    public ItemAdapter(List<Item> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        
        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
        
        void bind(final Item item) {
            textView.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
```

#### Kotlin
```kotlin
class ItemAdapter(
    private val items: List<Item>,
    private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount() = items.size
    
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)
        
        fun bind(item: Item) {
            textView.text = item.name
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}

// Avec View Binding
class ItemAdapter(
    private val items: List<Item>,
    private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount() = items.size
    
    inner class ViewHolder(private val binding: ItemLayoutBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: Item) {
            binding.textView.text = item.name
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}
```

---

## Asynchrone

### Threads

#### Java
```java
// Avec Thread
new Thread(new Runnable() {
    @Override
    public void run() {
        // Code en arrière-plan
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Mise à jour UI
            }
        });
    }
}).start();

// Avec ExecutorService
ExecutorService executor = Executors.newSingleThreadExecutor();
executor.execute(() -> {
    // Code en arrière-plan
});
```

#### Kotlin
```kotlin
// Avec Thread
Thread {
    // Code en arrière-plan
    runOnUiThread {
        // Mise à jour UI
    }
}.start()

// Avec Coroutines (moderne)
lifecycleScope.launch {
    // Code en arrière-plan
    val result = withContext(Dispatchers.IO) {
        // Opération longue
    }
    // Mise à jour UI (automatiquement sur le Main thread)
    textView.text = result
}
```

### Coroutines

#### Kotlin uniquement
```kotlin
// Dans une Activity/Fragment
lifecycleScope.launch {
    try {
        // Opération réseau
        val data = withContext(Dispatchers.IO) {
            fetchDataFromNetwork()
        }
        // Mise à jour UI
        updateUI(data)
    } catch (e: Exception) {
        showError(e.message)
    }
}

// Avec Flow
class UserRepository {
    fun getUsers(): Flow<List<User>> = flow {
        val users = fetchUsers()
        emit(users)
    }.flowOn(Dispatchers.IO)
}

// Utilisation
lifecycleScope.launch {
    repository.getUsers().collect { users ->
        updateUI(users)
    }
}
```

---

## Résumé des Différences Principales

| Fonctionnalité | Java | Kotlin |
|----------------|------|--------|
| Null Safety | Manuel (`if (x != null)`) | Natif (`?`, `?.`, `?:`) |
| Boilerplate | Verbose (getters/setters) | Minimal (properties, data class) |
| Lambdas | Depuis Java 8 | Native et concis |
| Extension functions | Non | Oui |
| Smart cast | Manuel | Automatique |
| String templates | Concaténation | `$variable` ou `${expression}` |
| Default parameters | Surcharge de méthodes | Paramètres par défaut |
| Named arguments | Non | Oui |
| When expression | `switch` limité | `when` puissant |
| Coroutines | Non (threads) | Native |
| Collections | Mutable par défaut | Immutable par défaut |
| Companion object | `static` | `companion object` |
| Singleton | Pattern manuel | `object` keyword |
| Type inference | Limité | Puissant |
| Checked exceptions | Oui | Non |

---

## Conseils de Migration

### 1. Commencez Progressivement
- Convertissez un fichier à la fois
- Java et Kotlin sont 100% interopérables
- Utilisez l'outil de conversion d'Android Studio (Code > Convert Java File to Kotlin File)

### 2. Apprenez les Idiomes Kotlin
- Utilisez `val` au lieu de `var` quand possible
- Profitez des fonctions d'extension
- Utilisez les fonctions de scope (`let`, `apply`, `with`, `run`, `also`)
- Préférez les expressions aux statements

### 3. Adoptez les Fonctionnalités Modernes
- View Binding au lieu de `findViewById()`
- Coroutines au lieu de AsyncTask
- Flow au lieu de LiveData (optionnel)
- Sealed classes pour les états

### 4. Ressources
- [Documentation officielle Kotlin](https://kotlinlang.org/docs/home.html)
- [Android Developers - Kotlin](https://developer.android.com/kotlin)
- [Kotlin Koans](https://play.kotlinlang.org/koans)
- [Kotlin Style Guide](https://developer.android.com/kotlin/style-guide)