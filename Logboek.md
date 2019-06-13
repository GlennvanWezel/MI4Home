# Logboek
## Note: halverwege door het project ben ik veranderd van repository omdat ik het project herstartte om het MVVM architectuur te volgen
### Zie vorig logboek: https://github.com/GlennvanWezel/HomeInventorizer/blob/master/logboek.md (U heeft hier toegang toe!)
#### uit zekerheid zal ik dat logboek hier copy pasten:

# TOTAAL AANTAL UREN ZOWEL PRE-MVVM ALS POST-MVVM: 152 uren

# LOGBOEK PRE-MVVM : 46.6 uren

# LOGBOEK POST-MVVM: 106 uren

# HomeInventorizer
Private Home inventory project (application)

# Werk voor semester 2
## Tijdens examens - 6u
* Idee uitwerken - 3,5u
* skelet files maken - 0.5u
* opzoekwerk voor navigatie systeem (jetpack navigation components) - 1u
* opzoekwerk voor navigation drawer - 1u
* opzoekwerk werkwijze 1 activity en meerdere fragmenten - 3u

## Na examens maar voor begin van semester 2 - 28u
* Idee documenteren - 2u
* GUI Low fidelity prototype maken in Balsamiq - 4u
* login/register activity maken - 1u
* Login/reigster fragmenten maken - 2u
* Nav Graph maken voor login activity - 0,5u
* Navigatie implementeren tussen startfragment, loginfragment en registerfragment - 2u
* Kern activity maken - 1u
* Kern fragmenten maken - 4u
* Nav Graph maken voor kern activity - 0,5u
* login implementeren adhv firebase - 7u (lang probleem met connectie naar firebase)
* registratie implementeren adhv firebase - 3u
* foto's kunnen uploaden naar firebase onder een bepaalde foldernaam op basis van ingelogde gebruiker (path: emailVanGebruiker/filename) - 1u

# Na Begin van semester 2
## Week 1 - 3.35u
* GUI elementen toevoegen aan overzicht fragment - 0,5u
### vrijdag 14/02 - 2.85u
* Passende GUI elementen opzoeken voor het beeld waarin men voorwerpen kan toevoegen (spinners = comboboxen) - 1u
* spinners toevoegen aan GUI - 0.05u
* opzoeken hoe ik best een list van strings als opties meegeef aan de spinner - 1u
* LOFI GUI prototype aanpassen - 0.3u
* met test list de spinners opgevuld om te zien of ze werken - 0.5u

## INACTIEVE PERIODE tussen 14/02 en 25/02
* dit komt doordat de startperiode van projecten zoals SE4 en AD4/DD4 al mijn tijd in beslag genomen heeft, elke dag vaak tot 22u savond werken afwisselend aan SE4 en dan AD4 liet weinig tijd over voor MI4, ten slotte was ik van 21/02 tot 25/02 ziek en niet in staat om te werken

## Week 3
### maandag 25/02 3.9u
* Miscellaneous verbeteringen in Toasts, destinatie na logout, etc - 0.2u
* opzoeken waarom fragemtn rendered over mijn toolbar - 0.3u
* uitpluizen waarom ik een toolbar zie terwijl er nergens expliciet een toolbar in de xml staat en er geen layout voor een toolbar gedefinieerd is.... 0.5u
* toolbars fixen zodat die maar 1 keer gedefinieerd staat in de activity, en zodat elk fragment er ONDER pas staat, en niet er op. 0.1u
* hamburgericon toevoegen aan toolbar adhv actionbardrawertoggle - 0.5u
#### SIDENOTE: heb al 3 keer android studio manueel via taskmanager moete killen want het hing vast en gebruikte 90% CPU, what the fuck man.
* fragment voor toevoegen van voorwerp vervolledigen, en eerste keer testen om voorwerp aan te maken (een object van klasse voorwerp aanmaken bedoel ik) - 0.5u
* proberen voorwerp object te doen opslagen op de firebase realtime database - 0.3u 
* opzoeken waarom "setvalue" niet bestaat tewrijl dit expliciet gebruikt word volgens de documentatie van firebase zelf. - 1u
* poging om voorwerp te kunnen opslagen in firebase database part 2: electric boogaloo - 0.5u

## Week 4
### Maandag 04/03/2019 - 2.5u
* foto kunnen selecteren uit gallerij - 0.5u
* foto opslagen op firebase - 1u
* error oplossen "Serializingt arrays is not supported, use lists instead" - 1u

# LOGBOEK POST-MVVM


### Woensdag 17/04/2019 - 7.75u
* MVVM Reso Coder tutorial bekijken (playlist: https://www.youtube.com/watch?v=yDaaM3u389I&list=PLB6lc7nQ1n4jTLDyU2muTBo8xk0dg0D_w ) - 2.75u
* MVVM structuur prepareren adhv tutorials - 3u
* MVVM bijsnoeien zodat het past met mijn project - 2u


### Donderdag 18/04/2019 - 1.5u
* Verderwerken aan MVVM patroon adhv nog een ander tutorial van Reso Coder - 1.5u

### Maandag 21/04/2019 - 2.5u
* Skeleton vervoledigen - 2u
* Navigation Schema maken - 0.5u

### Donderdag 24/04/2019 - 3u
* Add-Item pagina aanpassen/vervolledigen - 3u

### Woensdag 1/05/2019 - 7u
* Login fixen - 2u
* DIt was allemaal nutteloos want uiteindelijk gebruik ik toch gewoon de AuthUI.... - 5u

### Donderdag 02/05/2019 - 4u
* Entities (Models), DAO's, en Room database gemaakt - 4u

### Vrijdag 03/05/2019 - 5u
* Login systeem vervangen met FirebaseAuthUI - 3u
* nutteloos opzoekwerk om proberen te vinden hoe men de FIrebaseUI kan stylen - 2u

### Maandag 06/05/2019 - 5u
* Poging om verder MVVM structuur toe te passen door een firebaseservice klasse enzo aan te maken adhv reso coder tutorial - 5u

## TIJDENS DEZE DROP IN ACTIVITEIT KWAMEN ALLE DEADLINES VAN AD4, DD4, EN SE4 ER AAN EN MOEST IK ME DAAROP FOCUSEN

### Zaterdag 01/06/2019 - 6u
* uittesten van firestore - 2u
* opzoeken en bekijken van firestore tutorials - 3u
* rondspelen met firebase firestore DB om te testen hoe ik het best mijn objecten er in zou opslaan - 1u

### Zondag 02/06/2019 - 4u
* verder gesukkel met firestore - 2u
* verder gesukkel met MVVM sturctuur en het feit dat ik 2 verschillende tutorials door elkaar had toegepast - 2u

### Woensdag 05/06/2019 - 5u
* Database entities aanpasse - 1u
* poging om logout functie te creeeren - 0.5u
* Repo's verder aanvullen - 2u
* achterhalen in welke volgorde de viewmodellen, fragmenten gecreerd worden - 1.5u

### Donderdag 06/06/2019 - 2u
* ook repository gemaakt voor de Room en Type klassen uiteindelijk - 2u

### Zaterdag 08/06/2019 - 9u
* Goed in gang geschoten, te veel dinge om in detail te beschrijven maar in het algemeen de applicatie verbeterd en functionaliteiten toegevoegd - 5u
** functionaliteit: items kunnen toevoegen - 3u
** functionaliteit: items kunnen verwijderen -2u
* Opzoekwerk over Recyclerviews en hoe men er een maakt - 3u
* Recyclerview implementeren en opvullen met de items uit de DB - 1u

### Zondag 09/06/2019 - 9u
* project opschonen, onnodige bestanden wegdoen, dode code verwijderen, imports optimaliseren, etc - 2u
* HEEL IRRITANTE ERROR: ik verwijderde de app op mn gsm wanneer de kabel nog aangesloten was, en dan wanneer ik nogmaals de app doe runnen krijg ik type 3 error "Main Activity not found" also mijn ItemsActivity ni bestaat!!!!! - 5u
* mergen - 0.5u
* misc veranderingen -1.5u

### Maandag 10/06/2019 - 5u
* repositories verder aanvullen - 2u
* LiveData beter toepassen zodat de app reatiever is - 3u

### Woensdag 12/06/2019 - 18u
* Bug hunting - 6u
* repositories uitbreiding met getters voor livedata - 1u
* refactoring - 1u
* oplossingen zoeken en toepassen voor het feit dat de recyclerview geen items meer toonde - 2u
* Icoon van app en naam van app fixen - 2u
* Functionaliteit toevoegen en afronden:
** kamer/type toevoegen - 3u
* kleur schema veranderen - 1u
* layout verbeteren - 2u
 

### Donderdag 13/06/2019 - 11.5u
* layout verbeteren - 4u
* functionaliteiten toevoegen:
** verwijderen van kamer - 3u
** verwijderen van type - 3u
* wegwerken van warnings - 0.5u
* logboek aanvullen - 1u



