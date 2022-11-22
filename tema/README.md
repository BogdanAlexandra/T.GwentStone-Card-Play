

# Tema1 POO  - GwentStone

<div align=>Student: Bogdan Alexandra-Lacramioara</div>
<div align=>Student: Grupa: 325CD</div>

Punctul de plecare al acestui joc a fost sa inteleg cu ce il modelam, asadar fiecare joc are tabla sau masa de joc si jucatorii, fiecare jucator avand pachetul si cartile din acesta.

## Cartile
*    In acest joc exista mai multe tipuri de carți asignate unui  jucator , mai exact un jucator poate avea o carte de tipul Minion sau o carte de tipul Environment. De asemenea, fiecare jucator va avea o carte speciala de tipul Hero care il va reprezenta in joc.
* Asadar, clasa Card contine atributele comune acestor tipuri: mana, description, colors si name.
* Insa fiecare tip de carte contine alte carti care fac parte din tiparele lor.
*    Asadar pentru fiecare tip de carte cream o clasa, din care derivam (extindem) fiecare carte din tiparul clasei respective. 
*    Pentru cartea de tip Minion se poate observa ca nu am creat clasa propriu-zisa Minion, ci StandardMinionCard si SpecialMinionCard, deoarece avem 2 tipuri de carti Minion si asa e mai usor sa le manevram.

## Jucatorii
* Dupa cum stim fiecare jucator are mai multe pachete, prima lui sarcina fiind sa aleaga cu ce pachet incepe. Deci in clasa Player am implementat metodele prin care jucatorul isi alege pachetul de carti si in care i se atribuie eroul. Tot in interiorul acestei clase avem si metodele prin care se modeleaza mana fiecarui jucator.

## Decks/ Pachetele de carti 

* Pentru ca fiecare jucator are nevoie de un pachet de carti, in clasa Deck il cream si il pregatim pentru joc.

## Tratarea jocului
* Pregatirea si inceperea jocului se fac in clasa GwentStone, dupa modul in care ne este specificat in enunt.
* Functionarea efectiva a jocului se realizeaza prin metoda play.

## Tratarea Task-urilor
* Fiecare comanda pe care o primim in input se trateaza in clasa Action.

## Ce a fost cel mai greu?
* Ei bine, pentru mine, care va spun sincer nu sunt fan jocuri pe calculator, a fost sa inteleg exact modul de "lucru" al jocului.
* Una dintre problemele intampinate a fost la cartile Minion, deoarece avem 2 tipuri de acest tip. La inceput am facut doar o clasa Minion, ulterior mi-am dat seama ca doar mi-as ingreuna munca urmand  metoda aceasta.



## Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema)
