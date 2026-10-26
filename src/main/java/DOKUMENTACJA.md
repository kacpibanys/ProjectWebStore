# Dokumentacja internetowego sklepu z elektronikom. 

## 1. Opis biznesowy

Głównym założeniem biznesowym projektu było stworzenie sklepu internetowego z artykułami elektronicznymi, który będzie w pełni funkcjonalny. Obsługa sklepu odbywa się poprzez interfejs CLI, który umożliwia przeglądanie zawartości sklepu, konfigurowanie własnych setupów sprzętowych, przeglądanie koszyka oraz implementację kodów rabatowych. 

Aplikacja przygotowana jest również pod przetwarzanie dużej ilości zamówień jednocześnie dzięki zastosowaniu asynchronicznego przetwarzania. Uniemożliwia to tworzenie się kolejek i zapewnia sprawną obsługę każdego aktywnego w danym momencie klienta. 

Po każdym złożonym zamówieniu generowana jest faktura w folderze docelowym oraz log w pliku .csv, co umożliwia śledzenie wszystkich dotychczasowych zamówień. 

Wszelkie dane są przechowywane w stworzonych odpowiednio dla siebie repozytoriach typu in-built.

### Główne moduły i ich wartość biznesowa

| Moduł | Opis Funkcjonalności | Główna Wartość Biznesowa | 
| ----- | ----- | ----- | 
| **Zarządzanie magazynem** | Weryfikacja i aktualizacja stanów magazynowych w czasie rzeczywistym. | Zapobieganie sprzedaży towarów niedostępnych. | 
| **System rabatowy** | Obsługa kodów promocyjnych (zniżki procentowe i kwotowe). | Zwiększenie lojalności klientów oraz podtrzymywanie “relacji” klient-sklep. | 
| **Konfigurator produktów** | Konfigurator PC i smartfonów dobierający komponenty (RAM, CPU, GPU). | Możliwość sprzedaży  produktów spersonalizowanych. | 
| **Realizacja Zamówień** | Zapis faktur tekstowych i logów w pliku .csv | Bezpieczeństwo danych i automatyzacja procesów księgowych. | 

## 2. Opis technologii

Aplikacja zostala zbudowana bez użycia zewnętrznych frameworków. Jej architektura opiera się na Javie wraz z jej obiektowością oraz dziedziczeniem, co zapewnia wysoką kontrolę nad kodem. Do realizacji poszczególnych zadań wykorzystano następujące technologie i mechanizmy:

* **Java (Core & Collections Framework)** – Podstawa logiki biznesowej. Przechowuje dane w pamięci (repozytoria) np. `ConcurrentHashMap`, co zapewnia bezpieczeństwo danych w środowisku wielowątkowym.

* **Java Stream API** – Wykorzystane do czytelnego operowania na kolekcjach, np. przy iteracji po koszyku, walidacji stanów magazynowych i obliczaniu finalnej kwoty zamówienia.

* **Concurrency API (`ExecutorService` czy`CompletableFuture`)** – Technologia użyta do zrównoleglenia i asynchronicznego przetwarzania zamówień. Pozwala na delegowanie generowania faktur i zapisu plików do osobnych wątków, odciążając wątek główny aplikacji.

* **Java NIO (`java.nio.file`)** – Odpowiada za zapis dokumentów tekstowych (faktury) oraz generowanie i dopisywanie danych do plików logów.

* **JUnit 5** – Wykorzystany do tworzenia testów jednostkowych i integracyjnych. Gwarantuje poprawność logiki biznesowej oraz weryfikuje zyski wydajnościowe z implementacji wielowątkowości.

## 3. Przykład kodu 

Poniżej znajduje się listing kluczowej funkcjonalności  z **Taska 11**, obrazujący użycie `CompletableFuture` do asynchronicznej obsługi zamówień w klasie `OrderService`.

```
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderService {
    //Przygotowanie puli wątkow
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Metoda asynchronicznie przetwarza listę zamówień, a następnie 
     * odsyła każde zamówienie do wolnego wątku z puli.
     */
    public void processOrdersAsync(List<Order> orders) {
        // 1.Zlecenie przetwarzania każdego zamówienia w osobnym wątku
        List<CompletableFuture<Void>> futures = orders.stream()
                .map(order -> CompletableFuture.runAsync(() -> processOrder(order), executor))
                .toList();

        // 2.Oczekiwanie na zakończenie wszystkich operacji
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}

```