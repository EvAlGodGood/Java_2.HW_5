public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        float[] arr = new float[size];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        // для одного массива в одном методе
        arithmetic(arr);
        // для второго вынесено

        for (int i = 0; i < arr.length; i++ ){
            arr[i] = 1;
        }

        long b = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        System.out.println("Время разбивки " + (System.currentTimeMillis() - b));

        System.out.println("Start расчета двух потоков для разбитого массива.");

        long d = System.currentTimeMillis();
        Thread e1 = new Thread(new ArithmeticTread(arr1)); e1.start();
        Thread e2 = new Thread(new ArithmeticTread(arr2)); e2.start();

        try{
            e1.join();
            e2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Время параллельной работы потоков " + (System.currentTimeMillis() - d));

        long c = System.currentTimeMillis();

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);

        System.out.println("Время склейки " + (System.currentTimeMillis() - c));

    }

    public static void arithmetic (float[] arr){
        for (int i = 0; i < arr.length; i++ ){
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время одного потока " + (System.currentTimeMillis() - a));
    }

    public static class ArithmeticTread extends Thread {
        float[] arrAT;
        public ArithmeticTread(float[] arrAT){
            this.arrAT = arrAT;
        }

        @Override
        public void run(){
            long f = System.currentTimeMillis();
            for (int i = 0; i < arrAT.length; i++) {
                arrAT[i] = (float)(arrAT[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.out.println("Время одного потока разбитого массива " + (System.currentTimeMillis() - f));
        }
    }
}