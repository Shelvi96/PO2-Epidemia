# PO2-Epidemia

W jednoprocesorowym systemie operacyjnym zadaniem modułu szeregującego (zwanego planistą) jest wybieranie z kolejki procesów oczekujących kolejnego procesu, któremu zostanie przydzielony procesor. Proces, któremu zostanie przydzielony procesor usuwany jest z kolejki procesów oczekujących. O tym, czy proces wraca do kolejki procesów oczekujących decyduje to, czy całe zadeklarowane przez proces zapotrzebowanie na procesor zostało wykorzystane. Jeśli tak, proces opuszcza system, jeśli nie wraca do kolejki procesów oczekujących. Miejsce, na które wraca zależy od stosowanej strategii szeregowania.

Typowe strategie stosowane przez planistę to:

FCFS (ang. First Come, First Served) — procesor przydzielany jest procesom w kolejności ich pojawienia się w kolejce procesów oczekujących. W przypadku pojawienia się więcej niż jednego procesu w tej samej chwili czasu o kolejności decyduje identyfikator procesu (dla potrzeb tego zadania przyjmujemy, że identyfikatorem procesu jest liczba całkowita) - procesy o mniejszej wartości identyfikatora są umieszczane w kolejce wcześniej. Procesor pozostaje przydzielony procesowi do chwili wykorzystania przez proces całego deklarowanego zapotrzebowania na czas procesora.
SJF (ang. Shortest Job First) — procesor przydzielany jest procesowi deklarującemu najmniejsze spośród procesów oczekujących zapotrzebowanie na procesor (liczone w jednostkach czasu). W przypadku, gdy więcej niż jeden proces deklaruje najmniejsze zapotrzebowanie, decyduje identyfikator procesu - proces o mniejszej wartości identyfikatora wybierany jest jako pierwszy. Procesor pozostaje przydzielony procesowi do chwili wykorzystania przez proces całego deklarowanego zapotrzebowania na czas procesora.
SRT (ang. Shortest Remaining Time) — wybór następuje na zasadach analogicznych jak wyżej. W przypadku pojawienia się w kolejce procesów oczekujących procesu, który deklaruje zapotrzebowanie na procesor mniejsze niż pozostały do wykorzystania przez proces wykonujacy się odcinek czasu procesor jest odbierany procesowi wykonującemu się i przydzielany procesowi, który się pojawił. W przypadku, gdy pojawiający się proces deklaruje zapotrzebowanie identyczne z pozostałym do wykorzystania odcinkiem czasu procesu wykonującego się, procesor nie jest odbierany.
PS (ang. Processor Sharing) — wszystkie procesy zgłaszajace zapotrzebowanie na procesor otrzymują do niego dostęp natychmiast, przy czym przy n procesach ich faktyczne wykorzystanie procesora w ciągu jednostki czasu wynosi 1∕n. Oznacza to w szczególności, że tempo wykonywania się procesu może się zmieniać w zależności od liczby procesów wykonujących się. Każdy z procesów korzysta z procesora do czasu wykorzystania całego deklarowanego zapotrzebowania na procesor.
RR (ang. Round Robin)- procesy ustawiane są w kolejce cyklicznej zgodnie z kolejnością pojawiania się ich w systemie. Jeśli w systemie pojawia się więcej niż jeden proces w tej samej chwili czasu o miejscu w kolejce decyduje identyfikator procesu — proces o mniejszej wartości identyfikatora umieszczany jest w kolejce wcześniej. Procesor przydzielany jest procesom kolejno na q jednostek czasu. Po upływie czasu q proces, który się wykonywał umieszczany jest na końcu kolejki procesów oczekujących. Jeśli w chwili kończenia się odcinka q pojawi się nowy proces, umieszczany jest on w kolejce za procesem, któremu właśnie odebrano procesor.
Możliwe są również inne strategie.

Dla porównania efektywności poszczególnych strategii szeregowania stosowane są różne kryteria. Typowe to:

średni czas obrotu zadania — średni czas liczony od momentu pojawienia się zadania w kolejce zadań oczekujących do momentu zakończenia jego wykonywania.
średni czas oczekiwania — średni czas przebywania zadania w kolejce zadań oczekujących.
Istnieją również inne kryteria, ale dla zastosowania większości z nich, poza informacją początkową o kolejce zadań, wystarczy informacja o momencie zakończenia poszczególnych zadań w serii.

Zadanie
Należy napisać program Planista, który pozwoli na przeprowadzenie eksperymentów polegających na porównaniu efektywności wybranych strategii zgodnie z określonym kryterium.

Należy zaimplementować następujące strategie szeregowania: FCFS, SJF, SRF, PS, RR z różną wielkością q oraz oba wymienione kryteria porównywania efektywności.

Każdy z eksperymentów powinien polegać na symulacji wykonania zadanego ciągu zadań z wykorzystaniem wszystkich wymienionych strategii i ocenie ich efektywności zgodnie z wymienionymi kryteriami.
