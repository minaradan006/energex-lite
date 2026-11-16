# Energex Lite - Documentatie Arhitectura si Design OOP

Acest document descrie structura aplicatiei de procesare a datelor energetice si deciziile luate in timpul implementarii.

## 1. Structura Proiectului si Pachete

Aplicatia este impartita pe module pentru a separa clar responsabilitatile:
- `main`: Contine clasa `Main`, responsabila doar de pornirea aplicatiei, citirea datelor de intrare si salvarea fisierului JSON de iesire.
- `core`: Gestioneaza logica de business, fluxul comenzilor (Command Pattern) si parsarea intervalelor orare.
- `documents`: Modelele de date pentru tipurile de piata (preturi, incarcari, imbalante) si ierarhia de intervale.
- `repository`: Se ocupa de stocarea in memorie, interogari complexe pe intervale si regulile de validare.

## 2. Decizii de Design si Concepte OOP

### A. Command Pattern
Am ales sa folosesc Command Pattern pentru a evita un bloc `switch` sau `if-else` urias in clasa care proceseaza comenzile.
- Interfata `Command` defineste metoda comuna `execute(...)`.
- Clasele `ImportCommand` si `GetCommand` implementeaza separat logica pentru fiecare actiune.
- Avantaj: Daca apare o comanda noua in viitor, o pot adauga usor fara sa modific codul existent din `CommandProcessor`.

### B. Ierarhia de Clase si Polimorfismul
Datele de pe piata energetica au multe elemente comune, dar si specificitati.
- Am creat o clasa abstracta `BaseDocument` si o clasa de baza `Interval`.
- Fiecare tip de document isi suprascrie propria metoda `isValid()` pentru regulile lui de validare.
- Avantaj: Codul din repository si procesor nu trebuie sa verifice tipul exact al obiectului cu `instanceof`, ci se bazeaza pe polimorfism.

### C. Impartirea Componentelor din Repository
Initial, tot codul legat de stocare, cautare si reguli era adunat intr-o singura clasa mare (`MarketDataRepository`), ceea ce o facea greu de intretinut. Am spart-o in trei clase:
1. `MarketDataStore`: Tine lista de documente in memorie si face operatiunile de baza (adaugare, cautare dupa data/tip).
2. `MarketDataQueryService`: Contine algoritmii pentru cautarea in intervale de timp si gasirea ultimului start (`findLatestStart`).
3. `GridLoadValidator`: Izoleaza regula specifica pentru `GRID_LOAD` (verificarea ca diferenta de cantitate dintre doua intervale consecutive sa nu depaseasca 10000 MW).

## 3. Evolutia Arhitecturii si Probleme Intampinate

- Abordarea initiala: La inceput, am incercat sa scriu o mare parte din logica de parsare si validare in metode statice si clase utilitare (`MarketDataUtils`). Asta a dus la un cod greu de testat si am incalcat principiul de responsabilitate unica. De asemenea, metodele lungi faceau aproape imposibila trecerea regulilor stricte din Checkstyle.
- Schimbarea abordarii: Am renuntat la clasele utilitare si am mutat validarea direct in obiectele de domeniu (domain model). Fiecare obiect stie acum sa se valideze singur, iar parsarea a fost separata intr-un parser dedicat.
- Regulile Checkstyle: O alta provocare a fost adaptarea la cerintele stricte de formatare: adaugarea keyword-ului `final` pe parametri, extragerea numerelor magice in constante (ex: limitele de pret, durata intervalelor) si scrierea comentariilor Javadoc pentru toate metodele publice.