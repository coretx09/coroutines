# Multithreading and concurrency:
Jusqu'à présent, nous avons traité une application Android comme un programme avec un seul chemin d'exécution.
Vous pouvez faire beaucoup avec ce chemin d'exécution unique, mais à mesure que votre application se développe, vous
devez penser à la [concurrency]

[concurrency] permet à plusieurs unités de code de s'exécuter dans le désordre ou apparemment en parallèle,
ce qui permet une utilisation plus efficace des ressources.
Le système d'exploitation peut utiliser les caractéristiques du système, le _langage de programmation_ et le _concurrency unit_
pour gérer le _multitasking_.

[Thread] est la plus petite unité de code qui peut être planifiée et exécutée dans les limites d'un programme.
Vous pouvez créer un thread simple en fournissant un lambda.
le thread n'est pas exécuté tant que la fonction n'a pas atteint  start() appel de fonction.

[currentThread()] renvoie une Thread instance qui est convertie en sa représentation sous forme de chaîne qui renvoie le nom, la priorité et le groupe de threads du thread.

Alors qu'une application en cours d'exécution aura plusieurs threads, chaque application aura un thread dédié, spécifiquement
responsable de l'UI  de votre application. Ce fil est souvent appelé fil principal ou fil d'interface utilisateur.

# COROUTINES:
La création et l'utilisation de _threads_ pour les tâches en arrière-plan ont directement leur place dans Android,
mais Kotlin propose également des _coroutines_ qui offrent un moyen plus flexible et plus simple de gérer la simultanéité.

Les coroutines permettent le multitâche, mais offrent un autre niveau d'abstraction par rapport au simple travail avec les threads.
Une caractéristique clé des coroutines est la capacité de stocker l'état, afin qu'elles puissent être arrêtées et reprises.
Une coroutine peut ou non s'exécuter.

[kotlinx.coroutines] est une riche bibliothèque de coroutines développée par JetBrains.
Il contient un certain nombre de primitives de haut niveau activées par la coroutine que ce guide couvre, y compris 
_launch, async et d'autres_.

L'état, représenté par _continuations_, permet à des portions de code de signaler quand elles doivent passer le contrôle
ou attendre qu'une autre coroutine termine son travail avant de reprendre. Ce flux est appelé multitâche coopératif.

[CoroutineScope] Définit une portée pour les nouvelles coroutines. Chaque constructeur de coroutine (comme launch, async, etc.) 
est une extension sur CoroutineScope et hérite de son coroutineContext pour propager automatiquement tous ses éléments et son annulation.

[Dispatcher] gère le thread de sauvegarde que la coroutine utilisera pour son exécution, supprimant la responsabilité du développeur
quant au moment et à l'endroit où utiliser un nouveau thread.


# Coroutines Constructor:

[launch] is a coroutine builder.
Il _lance une nouvelle coroutine_ en même temps que le reste du code, qui continue de fonctionner indépendamment. 

[delay] is a special suspending function.
Il _suspend la coroutine pendant un temps précis_. La suspension d'une coroutine ne bloque pas le thread sous-jacent, 
mais permet à d'autres coroutines de s'exécuter et d'utiliser le thread sous-jacent pour leur code.

[runBlocking] est également un constructeur de coroutines qui relie le monde non-coroutine d'un régulier fun main() et 
le code avec des coroutines à l'intérieur de runBlocking {...}accolades.


# Structured concurrency
Les coroutines suivent un principe de concurrence structurée qui signifie que de nouvelles coroutines ne peuvent être lancées que 
dans un CoroutineScope spécifique qui délimite la durée de vie de la coroutine.
Dans une application réelle, vous lancerez de nombreuses coroutines. La simultanéité structurée garantit qu'ils ne sont 
pas perdus et qu'ils ne fuient pas. Une portée externe ne peut pas se terminer tant que toutes ses coroutines enfants ne sont pas terminées. 

# Extract function refactoring
[suspend function] peuvent être utilisées à l'intérieur des coroutines comme les fonctions normales, mais leur fonctionnalité supplémentaire 
est qu'elles peuvent, à leur tour, utiliser d'autres fonctions de suspension (comme delay()) pour suspendre l' exécution d'une coroutine.

# Scope builder
En plus de la portée coroutine fournie par différents constructeurs, il est possible de déclarer votre propre portée à l'aide du
constructeur _coroutineScope_. Il crée une étendue de coroutine et ne se termine pas tant que tous les enfants lancés ne sont pas terminés.

Les constructeurs _runBlocking_ et _coroutineScope_ peuvent se ressembler car ils attendent tous les deux que leur corps et tous ses enfants se terminent. 

La principale différence est que la méthode _runBlocking bloque le thread actuel pour l'attente_, tandis que _coroutineScope suspend simplement,
libérant le thread sous-jacent pour d'autres utilisations_. 

# Scope builder and concurrency
Un constructeur _coroutineScope_ peut être utilisé dans n'importe quelle fonction de suspension pour effectuer plusieurs opérations simultanées.