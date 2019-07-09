package matching;
import java.util.*;

public class Matching {
    
    public static int nr_noduri;
    public static LinkedList[]nod=new LinkedList[nr_noduri+1];
    
    public static LinkedList parcurge(int nod_curent, int[] saturat, int[] vizitat){
        
        LinkedList list = new LinkedList();
 
        Iterator it = nod[nod_curent].iterator();
        while(it.hasNext()){                     //se continua parcurgerea din primul nod adiacent nevizitat anterior, in scopul obtinerii unui drum cu noduri disjuncte fata de celelalte gasite
            int nod_urmator = (int) it.next();
            if(vizitat[nod_urmator]==0){
                vizitat[nod_urmator]=1;
                if(saturat[nod_urmator]==0){  //daca nodul este nesaturat, este intors drumul cu elementul in capul listei
                    list.add(nod_urmator);
                    return list;
                }
                list = parcurge(nod_urmator, saturat, vizitat); //daca nodul nu e saturat, se continua parcurgerea in mod recursiv
                list.add(nod_urmator);
                return list;
            }
        }
        
        return list; //acest caz reprezinta esecul gasirii unui nod nesaturat, fiind intoarsa lista vida
    }

    public static void main(String[] args) {
        
         Scanner sc = new Scanner(System.in);
         System.out.print("Numarul de noduri este: ");
         nr_noduri = sc.nextInt();
         int[] saturat = new int[nr_noduri+1];
         int nr_cuplaje= 0;
         
        nod=new LinkedList[nr_noduri+1]; //pentru fiecare nod in parte, avem o lista de adiacenta
        for(int i=0;i<=nr_noduri;i++){
             nod[i]=new LinkedList();
             saturat[i]=0;               //consideram de la inceput toate nodurile nesaturate
         }
        System.out.print("Numarul de muchii este: ");
        int muchii = sc.nextInt();
        
               
        // Graful bipartit este dat in forma unui graf orientat G = (X U Y, E)
        // Initial, toate muchiile pornesc de la un nod din X catre un nod din Y
        
        
        System.out.println("Introduceti muchiile orientate x->y : ");
        for(int i=0;i<muchii;i++){
            System.out.print("x=");
            int nod1=sc.nextInt();
            while(nod1==0){
                System.out.println("x nu poate fi 0");
                System.out.print("x=");
                nod1=sc.nextInt();
            }
            System.out.print("y=");
            int nod2=sc.nextInt();
            while(nod2==0){
                System.out.println("y nu poate fi 0");
                System.out.print("y=");
                nod2=sc.nextInt();
            }
            nod[nod1].add(nod2);
        }
        
        
        //Intruducem nodul 0 adiacent cu toate nodurile nesaturate din X(definit in comentariul de mai sus)
        for(int i=1;i<=nr_noduri;i++){
            if(nod[i].size()>0)
                nod[0].add(i);
        }
         
         int ok =1;
         while(ok==1){                                       //Algoritmul se repeta cat timp reuseste sa satureze noduri
            ok=0;
            Iterator it = nod[0].iterator();                 
            int[] vizitat = new int[nr_noduri+1];            //Vector ce contine valoarea 1 daca nodul de la pozitia k a fost vizitat si 0 altfel
            while(it.hasNext()){                             //Iteram pentru toate nodurile adiacente cu nodul 0(cautare BFS)
                int nod_curent = (int) it.next();
                if(saturat[nod_curent]==0){                  //Daca intalnim un nod nesaturat, incercam sa il saturam
                    LinkedList list = parcurge(nod_curent, saturat, vizitat); //Metoda intoarce un drum de la un nod nesaturat la nodul curent(sau de la un nod saturat la nodul curent, caz in care, ulterior, nu se va prelucra lantul) - parcurgere DFS
                    list.add(nod_curent);
             
                    Iterator it3 = list.iterator();
                    int valoare = (int) it3.next();
                    if(valoare!= nod_curent){                //Verificam daca lista contine mai mult de 1 element. In caz contrar elementul ramane nesaturat.
                        if(saturat[valoare]==0){             //Verificam daca elementul din capul listei este nesaturat. In caz contrar, nu se intampla nimic.
                            ok=1;
                            saturat[valoare]=1;              //Saturam nodul
                            while(it3.hasNext()){            //Se efectueaza inversarea orientarii muchiilor care alcatuiesc drumul
                                 int valoare2 = (int) it3.next();
                                 saturat[valoare2]=1;
                                 vizitat[valoare2]=1;
                                 nod[valoare].add(valoare2);
                                 nod[valoare2].removeFirstOccurrence(valoare);
                                 valoare=valoare2;
                             }
                         }
                     }
                }
            }
        }
         
         System.out.println("Muchiile care formeaza cuplaj sunt:");
         for(int i=1;i<=nr_noduri;i++){
             Iterator it2=nod[i].iterator();
             while(it2.hasNext()){
                 int x = (int) it2.next();
                 if(i > x)
                     System.out.println("Muchia "+x+" - "+i);
             }
         }
    }
    
}
