import java.security.PublicKey;

public class Mesa {
    private static final int PENSANDO = 1;
    private static final int COMENDO = 2;
    private static final int FOME = 3;
    private static final int NR_FILOSOFOS = 5;
    private static final int PRIMEIRO_FILOSOFO = 0;
    private static final int ULTIMO_FILOSOFO = NR_FILOSOFOS - 1;

    private boolean[] garfos = new boolean[NR_FILOSOFOS];
    private int[] filosofos = new int[NR_FILOSOFOS];
    private int[] tentativas = new int[NR_FILOSOFOS];

    public Mesa() {
        for(int i = 0; i < NR_FILOSOFOS; i++) {
            garfos[i] = true;
            filosofos[i] = PENSANDO;
            tentativas[i] = 0;
        }
    }

    public synchronized void getGarfos(int filosofo) {
        filosofos[filosofo] = FOME;
        while(filosofos[aEsquerda(filosofo)] == COMENDO || filosofos[aDireita(filosofo)] == COMENDO) {
            try {
                tentativas[filosofo]++;
                wait();
            } catch (InterruptedException e) {
            }
        }
        //System.out.println("O Filósofo morreu devido a starvation");
        tentativas[filosofo] = 0;
        garfos[garfoEsquerdo(filosofo)] = false;
        garfos[garfoDireito(filosofo)] = false;
        filosofos[filosofo] = COMENDO;
        imprimeEstadosFilosofos();
        imprimeGarfos();
        imprimeTentativas();
    }

    public synchronized void returnGarfos(int filosofo) {
        garfos[garfoEsquerdo(filosofo)] = true;
        garfos[garfoDireito(filosofo)] = true;
        if (filosofos[aEsquerda(filosofo)] == FOME || filosofos[aDireita(filosofo)] == FOME)
        {
            notifyAll();
        }
        filosofos[filosofo] = PENSANDO;
        imprimeEstadosFilosofos();
        imprimeGarfos();
        imprimeTentativas();
    }

    public int aDireita(int filosofo) {
        int direito;
        if(filosofo == ULTIMO_FILOSOFO) {
            direito = 0;
        }
        else {
            direito = filosofo + 1;
        }
        return direito;
    }

    public int aEsquerda(int filosofo) {
        int esquerdo;
        if(filosofo == PRIMEIRO_FILOSOFO) {
            esquerdo = ULTIMO_FILOSOFO;
        }
        else {
            esquerdo = filosofo - 1;
        }
        return esquerdo;
    }

    public int garfoEsquerdo(int filosofo) {
        int garfoEsquerdo = filosofo;
        return garfoEsquerdo;
    }

    public int garfoDireito(int filosofo) {
        int garfoDireito;
        if(filosofo == ULTIMO_FILOSOFO) {
            garfoDireito = 0;
        }
        else {
            garfoDireito = filosofo + 1;
        }
        return garfoDireito;
    }

    public void imprimeEstadosFilosofos ()
    {
        String texto = "*";
        System.out.print("Filósofos = [ ");
        for (int i = 0; i < NR_FILOSOFOS; i++)
        {
            switch (filosofos[i])
            {
                case PENSANDO :
                    texto = "PENSANDO";
                    break;
                case FOME :
                    texto = "FOME";
                    break;
                case COMENDO :
                    texto = "COMENDO";
                    break;
            }
            System.out.print(texto + " ");
        }
        System.out.println("]");
    }

    public void imprimeGarfos ()
    {
        String garfo = "*";
        System.out.print("Garfos = [ ");
        for (int i = 0; i < NR_FILOSOFOS; i++)
        {
            if (garfos[i])
            {
                garfo = "LIVRE";
            }
            else
            {
                garfo = "OCUPADO";
            }
            System.out.print(garfo + " ");
        }
        System.out.println("]");
    }

    public void imprimeTentativas ()
    {
        System.out.print("Tentou comer = [ ");
        for (int i = 0; i < NR_FILOSOFOS; i++)
        {
            System.out.print(filosofos[i] + " ");
        }
        System.out.println("]");
    }
}
