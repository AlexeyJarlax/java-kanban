#  java-sprint3-hw-kanban
Коммит №1.

Коммит №2. Внесены все исправления, в соответствии с замечаниями.


#  java-sprint4-hw-kanban
<details> 
  <summary> Техническое задание </summary>
Продолжим работать над трекером задач, который вы начали делать в прошлом спринте. Вам предстоит добавить в трекер новую функциональность, а также провести рефакторинг уже написанного кода с учётом изученных принципов ООП.

Менеджер теперь интерфейс. Из темы об абстракции и полиморфизме вы узнали, что при проектировании кода полезно разделять требования к желаемой функциональности объектов и то, как эта функциональность реализована. То есть набор методов, который должен быть у объекта, лучше вынести в интерфейс, а реализацию этих методов – в класс, который его реализует. Теперь нужно применить этот принцип к менеджеру задач. Класс TaskManager должен стать интерфейсом. В нём нужно собрать список методов, которые должны быть у любого объекта-менеджера. Вспомогательные методы, если вы их создавали, переносить в интерфейс не нужно. Созданный ранее класс менеджера нужно переименовать в InMemoryTaskManager. Именно то, что менеджер хранит всю информацию в оперативной памяти, и есть его главное свойство, позволяющее эффективно управлять задачами. Внутри класса должна остаться реализация методов. При этом важно не забыть имплементировать TaskManager, ведь в Java класс должен явно заявить, что он подходит под требования интерфейса.

История просмотров задач
Добавьте в программу новую функциональность — нужно, чтобы трекер отображал последние просмотренные пользователем задачи. Для этого добавьте метод getHistory() в TaskManager и реализуйте его — он должен возвращать последние 10 просмотренных задач. Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору — getTask(), getSubtask() и getEpic(). От повторных просмотров избавляться не нужно.

У метода getHistory() не будет параметров. Это значит, он формирует свой ответ, анализируя исключительно внутреннее состояние полей объекта менеджера. Подумайте, каким образом и какие данные вы запишете в поля менеджера для возможности извлекать из них историю посещений. Так как в истории отображается, к каким задачам было обращение в методах getTask(), getSubtask() и getEpic(), эти данные в полях менеджера будут обновляться при вызове этих трех методов.
Обратите внимание, что просмотрен может быть любой тип задачи. То есть возвращаемый список задач может содержать объект одного из трех типов на любой своей позиции. Чтобы описать ячейку такого списка, нужно вспомнить о полиморфизме и выбрать тип, являющийся общим родителем обоих классов.

Утилитарный класс
Со временем в приложении трекера появится несколько реализаций интерфейса TaskManager. Чтобы не зависеть от реализации, создайте утилитарный класс Managers. На нём будет лежать вся ответственность за создание менеджера задач. То есть Managers должен сам подбирать нужную реализацию TaskManagerи возвращать объект правильного типа.
У Managersбудет метод getDefault(). При этом вызывающему неизвестен конкретный класс, только то, что объект, который возвращает getDefault(), реализует интерфейс TaskManager.

Статусы задач как перечисление. Так как варианты возможных статусов у задачи ограничены, для их хранения в программе лучше завести перечисляемый тип enum.

Тестирование вашего решения.
Убедитесь, что ваше решение работает! В главном классе воспроизведите несложный пользовательский сценарий:
создайте несколько задач разного типа.
вызовите разные методы интерфейса TaskManager и напечатайте историю просмотров после каждого вызова. Если код рабочий, то история просмотров задач будет отображаться корректно.
Сделайте историю задач интерфейсом.
В этом спринте возможности трекера ограничены — в истории просмотров допускается дублирование и она может содержать только десять задач. В следующем спринте вам нужно будет убрать дубли и расширить её размер. Чтобы подготовиться к этому, проведите рефакторинг кода.
Создайте отдельный интерфейс для управления историей просмотров — HistoryManager. У него будет два метода. Первый add(Task task) должен помечать задачи как просмотренные, а второй getHistory() — возвращать их список.
Объявите класс InMemoryHistoryManager и перенесите в него часть кода для работы с историей из класса InMemoryTaskManager. Новый класс InMemoryHistoryManager должен реализовывать интерфейс HistoryManager.
Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory(). Он должен возвращать объект InMemoryHistoryManager — историю просмотров.
Проверьте, что теперь InMemoryTaskManager обращается к менеджеру истории через интерфейс HistoryManager и использует реализацию, которую возвращает метод getDefaultHistory().
Ещё раз всё протестируйте!
  </details>
  
Коммит №1.

Коммит №2. Внесены исправления, в соответствии с замечаниями.


#  java-sprint5-hw-kanban
<details> 
  <summary> Техническое задание </summary>
  Пришло время потренироваться и усовершенствовать код трекера с помощью полученных знаний о списках и хеш-таблицах! В этом спринте вам предстоит поработать с историей просмотров задач, а именно избавиться от повторных просмотров в ней и ограничения на размер истории. Поехали!
  
Обратите внимание. Недостаточно реализовать код таким образом, чтобы программа пробегалась по всей истории просмотров и только после этого удаляла предыдущий просмотр. Ведь тогда время работы этой программы будет линейно зависеть от длины истории. Ваша цель — реализовать функциональность так, чтобы время просмотра задачи никак не зависело от общего количества задач в истории. 

Интерфейс HistoryManager. У нас уже есть интерфейс, осталось добавить метод void remove(int id) для удаления задачи из просмотра. И реализовать его в классе InMemoryHistoryManager. Добавьте его вызов при удалении задач, чтобы они также удалялись из истории просмотров.

Дальнейшая разработка алгоритма с CustomLinkedList и HashMap. Программа должна запоминать порядок вызовов метода add, ведь именно в этом порядке просмотры будут выстраиваться в истории. Для хранения порядка вызовов удобно использовать список. Если какая-либо задача просматривалась несколько раз, в истории должен отобразиться только последний просмотр. Предыдущий просмотр должен быть удалён сразу же после появления нового — за O(1). Из темы о списках вы узнали, что константное время выполнения операции может гарантировать связный список CustomLinkedList. Однако его стандартная реализация в данном случае не подойдёт. Поэтому вам предстоит написать собственную. CustomLinkedList позволяет удалить элемент из произвольного места за О(1) с одним важным условием — если программа уже дошла до этого места по списку. Чтобы выполнить условие, создайте стандартную HashMap. Её ключом будет id задачи, просмотр которой требуется удалить, а значением — место просмотра этой задачи в списке, то есть узел связного списка. С помощью номера задачи можно получить соответствующий ему узел связного списка и удалить его. Реализация метода getHistory должна перекладывать задачи из связного списка в ArrayList для формирования ответа.

Тестирование работы программы. После написания менеджера истории проверьте его работу: создайте две задачи, эпик с тремя подзадачами и эпик без подзадач; запросите созданные задачи несколько раз в разном порядке; после каждого запроса выведите историю и убедитесь, что в ней нет повторов; удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться; удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
  </details>
  
Коммит №1.  

Коммит №2. Внесены исправления, в соответствии с замечаниями.


#  java sprint 6 homework kanban
<details> 
  <summary> Техническое задание </summary>
   В этом спринте вы добавите в трекер задач ещё одну полезную опцию. Текущая реализация хранит состояние менеджера в оперативной памяти, из-за этого после перезапуска приложения все нужные нам данные теряются. Решить эту проблему может такой класс менеджера, который будет после каждой операции автоматически сохранять все задачи и их состояние в специальный файл.
Вам предстоит создать вторую реализацию менеджера. У него будет такая же система классов и интерфейсов, как и у нынешнего. Новый и старый менеджеры будут отличаться только деталями реализации методов: один хранит информацию в оперативной памяти, другой — в файле.

Вторая реализация менеджера. Итак, создайте класс FileBackedTasksManager. В нём вы будете прописывать логику автосохранения в файл. Этот класс, как и InMemoryTasksManager, должен имплементировать интерфейс менеджера TasksManager.
Теперь нужно написать реализацию для нового класса. Если у вас появится желание просто скопировать код из InMemoryTasksManager и дополнить его в нужных местах функцией сохранения в файл, остановитесь! Старайтесь избегать дублирования кода, это признак плохого стиля.
В данном случае есть более изящное решение: можно наследовать FileBackedTasksManager от InMemoryTasksManager и получить от класса-родителя желаемую логику работы менеджера. Останется только дописать в некоторых местах вызовы метода автосохранения.

Метод автосохранения. Пусть новый менеджер получает файл для автосохранения в своём конструкторе и сохраняет его в поле. Создайте метод save без параметров — он будет сохранять текущее состояние менеджера в указанный файл.
Теперь достаточно переопределить каждую модифицирующую операцию таким образом, чтобы сначала выполнялась версия, унаследованная от предка, а затем — метод save. Например:

Затем нужно продумать логику метода save. Что он должен сохранять? Все задачи, подзадачи, эпики и историю просмотра любых задач. Для удобства работы рекомендуем выбрать текстовый формат CSV (англ. Comma-Separated Values, «значения, разделённые запятыми»). 

Сначала через запятую перечисляются все поля задач. Ниже находится список задач, каждая из них записана с новой строки. Дальше — пустая строка, которая отделяет задачи от истории просмотров. И заключительная строка — это идентификаторы задач из истории просмотров.
Файл из нашего примера можно прочитать так: в трекер добавлены задача, эпик и подзадача. Эпик и подзадача просмотрены и выполнены. Задача осталась в состоянии новой и не была просмотрена.

Итог. У вас должно появиться несколько новых классов, а также новый менеджер с опцией сохранения состояния. Убедитесь, что он работает корректно, и отправляйте свой код на ревью.
</details>

Коммит №1. Реализация автосохранения в файл в соответствии с требованиями ТЗ.  

* Много непонятных коммитов, Пытался разобраться в работе git merge и как работают ветки
