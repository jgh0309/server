import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class javaserversocket {
    public static void main(String[] args){

        try{
            ServerSocket server = new ServerSocket(11001);
            System.out.println("서버 소켓이 만들어졌습니다. 포트 : 11001");

            while(true){ //클라이언트가 접속할 때까지 기다림
                Socket aSocket = server.accept();
                System.out.println("클라이언트와 연결됨.");

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream()); //클라이언트 소켓에서 날아온 정보중 헤더부분을 제외한 데이터를 읽어들임
                Object obj = instream.readObject();                                               // 정보=헤더(소켓,ip등 목적지 정보)+데이터
                System.out.println("받은 데이터 : "+ obj);

                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream()); //서버가 클라이언트에 정보를 받았다는 답변을 보냄
                outstream.writeObject(obj+" from server.");
                outstream.flush();                                                                  //버퍼가 찰 때까지 기다리지 않고 데이터를 즉시 전송함
                System.out.println("보낸 데이터 : "+ obj+" from server.");
                aSocket.close();
                System.out.println("소켓 닫음.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}