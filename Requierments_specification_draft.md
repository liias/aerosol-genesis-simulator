<b>Aerosol Genesis Simulation</b>

<b>Sissejuhatus</b>

Käesolev tarkvara nõuete spetsifikatsiooni dokument kirjeldab funktsionaalseid ja mittefunktsionaalseid nõudeid tarkvarale, mille abil hakatakse kasutama olemasolevat aerosooli nukleatsiooni modelleerimise programmi (edaspidi HT programm).
HT programmist

HT programm on kirjutatud Pascali programmeerimiskeeles ning see modelleerib atmosfääris tekkivaid aerosooli nukleatsioonipuhanguid. HT programm ise on mõeldud ühe juhtumi läbiarvutamiseks. Tema  automatiseeritud kasutamiseks on tarvilik nn. kest, mis varustab  HT programmi sobival viisil etteantava juhtumite reaga ja salvestab (ning vajadusel töötleb) HT programmi poolt genereeritud üksikjuhtumite arvutuste tulemused.

Täpsemalt, programmile tuleb ettenähtud formaadis tekstifailina (burstcontrol.txt) ette anda arvulised lähteparameetrid ning  nende baasil teostab programm arvutused. Programmi töö tulemuseks on signatuurifail (ht\_burst.txt) ning tulemuste fail -  maatriksi kujul arvulised väärtused (ht\_burst.xl). Signatuuri fail ei oma antud juhul sisulist tähtsust, kuna kujutab endast juba teada lähteväärtuste lugemit. Küll aga on väga oluline tulemuste fail.

Loodav tarkvara võimaldab kasutajal lihtsalt modelleerida aerosooli nanoosakeste puhangulist teket ja evolutsiooni ning modelleerimistulemusi võrrelda kasutaja poolt ette antud näidisväärtustega. Kasutajal on esmane graafiline tööaken, millest ta saab avada teisi aknaid, kus on eri andmegrupid mille abil saab ta siestada modelleeerimise lähteväärtusi, või siis esmasest aknast saab ta veel salvestada ja laadida  modelleerimise jaoks lähteväärtusi ning määrata modelleerimistsüklite arvu.
Tarkvara salvestab ka lähteväärtused ja vastavad tulemused andmebaasi hilisemaks analüüsiks.

Loodavat tarkvara lahendust hakkaksid kasutama keskkonnafüüsikud ja füüsika tudengid. Eraldi kasutajate gruppe ei ole. Kasutaja paigutab ja käivitab tarkvara enda arvutist. Arvutustulemused salvestatakse kas kasutaja arvutisse või võrgukettale.



<b>**Funktsionaalsed nõuded</b>**

Modelleerimine

**Kasutusloo ID:1**


Kasutusloo nimi: Arvutustsükkel valikumenüü põhjal


Koostatud:Indrek Ploom


Viimati muudetud: Kuupäev:17.09.12


Kirjeldus: Modelleeritakse kasutades valikumenüüst saadud lähteväärtusi


Eeltingimused:   Programm on avatud;
> Kindlasti ei ole valitud failist lähteväärtuste võtmist.


Prioriteet:1


Normaalne sündmuste käik:
> Kui kasutaja vajutab nuppu „Defaults“ kirje „combobox values“ järel, siis valikumenüüs on väärtused seatud vaikeväärtusteks. Vajutades nuppu „Simulate“ simuleerimine õnnestub;
> Kui kasutaja vajutab nuppu „Clear“ kirje „combobox values“ järel, siis tühjendatakse kõik valikumenüü väärtused. Simuleerimine ei õnnestu;
> Kui kasutaja taas valib vaikeväärtused ja seejärel kas või ühe väärtuse seab tagasi algseisu, siis simuleerimine ei õnnestu;
> Kui kasutaja seab vaikeväärtused ning seejärel muudab ühe kuni kõigi parameetri väärtusi suvaliselt (), siis simuleerimine õnnestub.
> Kasutaja valib „Number of runs“ valikumenüüst väärtuse, mis on >1. Simuleerimine õnnestub ja nii mitu korda simuleeritakse kui valiti.


Erandid: -


Erilised nõuded:
> „Nadykto-Yu dipoole enchant coeff.“ peab olema väiksem kui talle eelnev väärtus väljas „Nadykto“-Yu dipoole enchant factor“


Märkused:
> Kasutaja ei saa kõiki HT programmi lähteväärtusi (mida võib lugeda failist burstcontrol.txt) ise seadistada. Neid, mida kasutaja ei tohi muuta, neid ta ka programmi aknas ei näe ja need väärtused on programmi koodi sisse kirjutatud.
> Neid lähteväärtusi, mida kasutaja saab muuta, nendel on ka omad lubatud piirid (nähtavad valikumenüüst, koos sammuga).


  * Failist lähteväärtuste võtmist ei tohi olla valitud, sest failist väärtuste võtmine on kõrgema prioriteediga ja sellisel juhul võtab programm väärtused failist ja valikumenüüdest väärtusi ei uurita.
> „Simuleermine õnnestus“ tähendab seda, et on loodud õigete väärtustega burstcontrol.txt fail, HT programmiga on töö tehtud ja andmebaasi on ilmunud vastav lähteväärtuste komplekt koos seotud tulemustega.


**Kasutusloo ID:2**


Kasutusloo nimi: Arvutustsükkel min/max välja põhjal


Koostatud:Indrek Ploom


Viimati muudetud: Kuupäev: 17.09.12


Kirjeldus: Modelleeritakse kasutades „min“ ja „max“ väljalt saadud väärtusi


Eeltingimused:    Programm on avatud;
> Kindlasti ei ole valitud failist lähteväärtuste võtmist;
> Valikumenüüdes ei ole valitud väärtusi.


Prioriteet:1


Normaalne sündmuste käik:
> Kui kasutaja vajutab nuppu „Defaults“ kirje „min/max values“ järel, siis vastavatel väljadel on väärtused seatud vaikeväärtusteks. Vajutades nuppu „Simulate“ simuleerimine õnnestub;
> Kui kasutaja vajutab nuppu „Clear“ kirje „min/max values values“ järel, siis kaotatakse kõik vastavate väljade väärtused. Simuleerimine ei õnnestu;
> Kui kasutaja taas valib vaikeväärtused ja seejärel kas või ühe väärtuse kustutab, siis simuleerimine ei õnnestu;
> Kui kasutaja seab vaikeväärtused ning seejärel muudab ühe kuni kõigi parameetri väärtusi juhuslikult (), siis simuleerimine õnnestub.
> Kasutaja valib „Number of runs“ valikumenüüst väärtuse, mis on >1. Simuleerimine õnnestub ja nii mitu korda simuleeritakse kui valiti.


Erilised nõuded:
> Kasutaja võib sisestada väljadesse nii täisarve kui ka komakohaga arve. Programm konverteerib väärtused ise vajadusel ümber.


Märkused:
> Min/max väljade vaikeväärtused tähistavad samas ka nende väljade lubatud absoluutset miinimum- ja maksimumväärtusi. Kui kasutaja sisestab väljale „min“ väiksema väärtuse kui vaikeväärtus, siis simuleerimise käigus muudetakse see väärtus automaatselt vaikeväärtuse miinimumiks. Analoogselt käitutakse  „max“ väljal seatud väärtusega kui see ületab „max“ vaikeväärtust.
> Valikumenüüs ei tohi olla väärtusi valitud, sest valikumenüüst väärtuste võtmine on kõrgema prioriteediga kui min/max väljadelt väärtuste võtmine.


**Kasutusloo ID:3**


Kasutusloo nimi:Arvutustsükkel kombineeritud väärtuste põhjal


Koostatud:Indrek Ploom


Viimati muudetud: Kuupäev:17.09.12


Kirjeldus: Modelleeritakse kasutades lähteväärtusi nii valikumenüüst
kui ka „min“ ja „max“ väljadelt


Eeltingimused: Programm on avatud.


Prioriteet:1


Normaalne sündmuste käik:
> Kasutaja seab valikumenüüdest juhuslikke väärtusi nii, et vähemalt ühel juhul jääb valikumenüüs väärtus valimata ja selle sama välja järel seab kasutaja min/max väärtused. Simuleerimine õnnestub.
> Korratakse 1. punkti, aga nii, et vaid ühel juhul on valikumenüüst väärtus valitud. Simuleerimine õnnestub.
> Kasutaja valib „Number of runs“ valikumenüüst väärtuse, mis on >1. Simuleerimine õnnestub ja nii mitu korda simuleeritakse kui valiti.
> Korratakse 1. punkti, aga nii, et vähemalt ühel juhul jääb valikumenüüs väärtus valimata ja samuti ka vastavatesse min/max väljadesse sisestamata. Simuleerimine ei õnnestu.


Erilised nõuded:-


Märkused:
> Programmi lähteväärtuste valiku põhimõte: kui on valitud failist lähteväärtuste võtmine, siis võetakse sealt. Kui ei ole, siis võetakse valikumenüüst. Kui seal ei ole väärtus(ed) valitud, siis võetakse väärtused min/max väljadest. Kui seal ei ole, siis programm ei modelleeri.


**Kasutusloo ID: 4**


Kasutusloo nimi: Arvutustsükkel failist saadud väärtuste põhjal


Koostatud: Indrek Ploom


Viimati muudetud: Kuupäev: 17.09.12


Kirjeldus: Modelleeritakse kasutades failist loetud lähteväärtusi


Eeltingimused:
> Programm on avatud;
> Kasutaja arvutis on valmis lähteväärtuste komplekt, kus on üks rida vastavate lähteväärtustega;
> Kasutajal on arvutis valmis lähteväärtuste komplekt, kus on mitu rida ehk mitu komplekti lähteväärtusi;
> Kasutajal on ka ebakorrektne lähtefail.


Prioriteet: 1


Normaalne sündmuste käik:
> Kasutaja valib „Take values from file“ ja avaneb dialoogi aken, kus küsitakse lähtefaili asukohta;
> Kui kasutaja valib ebakorrektse lähtefaili, siis simuleerimine ei õnnestu;
> Kui kasutaja valib lähteväärtustega faili, kus on üks komplekt, siis simuleerimine õnnestub;
> Kui kasutaja valib lähteväärtuste faili, kus on palju lähtekomplekte, siis simuleerimine õnnestub ja nii, et kõik failis olevad komplektid töötatakse läbi.


Erilised nõuded: „Number of runs“ antud juhul ei avalda mõju.


Märkused:
> Programm loeb lähteväärtuste komplekte rea haaval. Nii mitu rida on, nii mitu simuleerimist ka tehakse. Lähtefailis on väärtused märgivoona, s. t. failis on loetavad numbrid.
Mallide kasutamine


**Kasutusloo ID:5**


Kasutusloo nimi: Töö defineeritud väärtuskomplektidega


Koostatud:Indrek Ploom


Viimati muudetud: Kuupäev:17.09.12


Kirjeldus: Lähteväärtuste komplektide salvestamine ja laadimine


Eeltingimused: Programm on avatud;


Prioriteet:1


Normaalne sündmuste käik:
> Kasutaja seab valikumenüüdesse juhuslikud väärtused ja vajutab nuppu „Save“ kirje „combobox values“ järel.  Kasutajale avaneb dialoogi aken, kus ta saab seatud väärtuste komplekti salvestada enda arvutisse;
> Kasutaja teeb muutusi seatud väärtustest või vajutab nuppu „Clear“ kirje „combobox values“ järel ja seejärel vajutab nuppu „Open“. Kasutajale avaned dialoogi aken, kust ta saab laadida varemsalvestatud valikumenüü väärtuste komplekte programmi aknasse;
> Sammude 1 ja 2 kordamine, aga seekord salvestada ja laadida min/max väljade väärtusi ning vajutada vastavaid nuppe kirje „min/max values“ järel.


Erilised nõuded:-


Märkused:Salvestada võib laiendiga või faililaiendita.



Andmete analüüs

**Kasutusloo ID:6**


Kasutusloo nimi:Tulemuste analüüs


Koostatud:Indrek Ploom


Viimati muudetud:Kuupäev:17.09.12


Kirjeldus: Varasema töö tulemuste võrdlemine etteantud referentsväärtustega


Eeltingimused: Programm on avatud;
> Eelnevalt on vähemalt üks arvutustulemuste komplekt andmebaasis, mis on ka piisavalt pikk, s. t. simuleeritud aeg on vähemalt nii pikk kui on referentsväärtuse pikkus.


Prioriteet: 1


Normaalne sündmuste käik:

> Kasutaja vajutab nuppu „Compare“. Seejärel avaneb dialoogi aken, kus kasutaja saab laadida referentsväärtustega faili. Toimub tulemuste analüüs, kus programm käib läbi kõik andmebaasis olevad tulemused ja väljastab kasutajale 10 parimat lähteväärtuste komplekti faili paremuse alusel ülevalt alla reana.
> Kasutaja vajutab nuppu „Set best“. Kasutajale seatakse programmi aknasse min/max väljadesse parim lähteväärtuste komplekt failist ehk esimene rida.


Erilised nõuded:  Referentsväärtuste fail on


Märkused:
> Tulemuste analüüs tähendab protsessi:
> > kus uuritakse kui pikk on võrdlusesse minev referentsmaatriks;
> > referentsmaatriksi esimesest reast loetakse vastavate tulpade kaalud, ülejäänud osas on ref maatriks otseselt võrreldav andmebaasi tulemuste maatriksitega;
> > referentstabeli ülejäänud (kõik peale kaalude) osa loetakse võrdluseks sisse;
> > uuritakse andmebaasist milline on minimaalne ja maksimaalne tulemuste komplektide id;
> > algab tsükkel minimaalsemast leitud komplektist kuni maksimaalseni:
> > > kui vastav tulemuste komplekt ei ole vähemalt nii pikk kui on referents, siis seda komplekti ei võrrelda, sest puudub võimalus kõiki elemente võrrelda;
> > > kui vastava id numbriga komplekti ei eksisteeri, siis minnakse järgmist otsima;
> > > kui tulemuste komplekt on olemas, siis loetakse sealt referentsmaatriksiga võrdne osa ja teostatakse iga maatriksi komponentide vahel kaalude vahe arvutamise operatsioon. Kõik erinevuste summad liidetakse lõpuks kokku;
> > > kui suma on väiksem kui seni 10 parimat, siis tunnistatakse see komplekt parimate hulka ja jäetakse meelde, kusjuures ka see mitmes ta paremustabelis on;
> > > kui kõik tulemused on andmebaasist läbi käidud, siis kirjutatakse faili 10 parimat lähteväärtuste komplekti nii, et iga komplekt omaette reas ja paremusjärjestuse alusel ning iga komplekti saadud kogusumma lõpus koos vastava andmebaasi id numbriga.

> Kaalude vahe arvutamine on matemaatiline operatsioon, kus käiakse üksteise järel läbi kõik referentsmaatriksi elemendid, lahutatakse igaühest vastava tulemustemaatriksi element, võetakse vahe ruutu ja korrutatakse vastava tulba kaaluga läbi. ∑=[(elRef - elTul)²]kaal. Tulba kaal on on täisarv 0-10.


Andmete eksport/import

**Kasutusloo ID:7 POOLIK**


Kasutusloo nimi:Andmebaasis paiknevate andmete eksportimine/importimine


Koostatud:Indrek Ploom


Viimati muudetud: Kuupäev : 17.09.12


Kirjeldus: Andmebaasis olevate andmete eksportimine / importimine


Eeltingimused:
> Programm on avatud;
> Eelnevalt on vähemalt üks arvutustulemuste komplekt andmebaasis.


Prioriteet: 1


Normaalne sündmuste käik:
> Kasutaja vajutab nuppu „Export“. Programm uurib andmebaasist kui mitu lähteväärtuste komplekti üldse on ja tagastab


Erilised nõuded:
> Kõik vood on märgivood, s. t. tekstifailina ja väärtused on sees numbritega


Märkused:


<b>**Mittefunktsionaalsed nõuded</b>**


**Kasutusmugavus**

> Tarkvara paigaldamine arvutisse ei nõua mingeid erialaseid teadmisi ja see ei nõua üle 5 hiire kliki ega võte üle 2 minuti;
> Tarkvara paigaldamine ei nõua arvutis administraatori õigusi;
> Kasutaja saab valida kuhu kataloogi ta rakenduse paigaldab;
> Tarkvaraga peab olema võimalik tööd teha hiljemalt 2 minu pärast peale programmi käivitamist;
> Tarkvara töötamise põhimõtetest on võimalik aru saada 5 minuti jooksul;
> Tarkvara eemaldamine, DB puhastamine üles funkts. alla?

**Jõudlus**

> Tarkvara suudab järjest teha vähemalt 1000 modelleerimist juhuslike väärtuste baasil;
> Tarkvara suudab järjest teha vähemalt 1000 modelleerimist valikumenüüst valitud väärtuste baasil;
> Tarkvara suudab järjest teha vähemalt 1000 modelleerimist kasutades väärtusi nii valikumenüüst kui ka min/max väljadest;
> Tarkvara suudab järjest teha vähemalt 100 modelleerimist kasutades failina etteantud lähteväärtuste komplekte;
> Tarkvara suudab teostada referentsväärtuste võrdlemise andmebaasis olevate väärtustega ka siis kui referentsväärtuste simuleerimise pikkus on maksimaalne ehk 1440 minutit ja andmebaasis on vähemalt 100 nii pikka arvutustulemuste komplekti;
> Üksik simuleerimine ei võta kauem kui 5 minutit, isegi kui simuleeritakse 1440 minutit (HT programmi algoritmi mõjutada ei saa, aga sellest tundub täitsa piisavat);
> Tarkvara suudab eksportida / importida andmebaasist vähemalt 10 000 rida andmeid;

**Muud**

> Turvalisus - Antud programmiga ei tule kaasa kasutaja nime ja parooli küsimist;
> Platform – antud lahendus töötab Windows XP/7/8 32/64bit operatsioonisüsteemidega, eelduseks on Java olemasolu arvutis;
> Tarkvara töö tulemustest ega andmebaasist varukoopiaid ei tee;
> Kui Tarkvara suletakse, siis ka andmebaas, mida tarkvara kasutab, sulgub;

<b>**Tehnilised nõuded</b>**

> Tarkvara paigaldatakse kasutaja arvutisse;
> Kasutaja käivitab programmi oma arvutist ühest failist;
> Nii programm kui ka andmebaas asuvad kasutaja arvutis;
> Kasutajal on võimalus salvestada tulemused ja vastavad lähteväärtused välisesse andmebaasi juhul, kui väline andmebaas on saadaval;

**Pooleni olevad probleemid:**

> Kuidas teostada osa või kõigi kirjete eksporti/importi?
> Kuidas teostada ühe nadmbeaasi kirjete transporti teise andmebaasi?
> Peaks saama kasutada ka välis db-s, näiteks Myswl või PostGre?
> Sama andmebaasi peaks saama kasutada mitu erinevat arvutit oma progedega?
> HT programmi sisendparameetrite võimalik muutus? Siis ju kõik vana ei klapi ja oluline on ka see, et „comapre“ pange ei paneks. Sisendaparameetrite komplekt ju peab olema proge koodis..

Tuleb teada anda, mida teostada ei saa ja et miks.