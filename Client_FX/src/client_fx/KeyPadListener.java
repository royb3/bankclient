package client_fx;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author roy
 */
public class KeyPadListener {
    private ButtonPressedListener bpListener = null;
    private CardSwipeListener csListener = null;
    private static KeyPadListener instance = null;
    private SerialPort port;
    private String accountID;
    private String cardNumber;
    public static KeyPadListener getListener(String portName){
        if(instance == null)
        {
            instance = new KeyPadListener(portName);
        }
        return instance;
    }
    
    public static KeyPadListener getListener() throws Exception{
        if(instance == null)
        {
            throw new Exception("No listener present and no port name given :(");
        }
        return instance;
    }
    public void setKeyPressedListener(ButtonPressedListener listener)
    {
        this.bpListener = listener;
    }
    
    public void setCardSwipedListener(CardSwipeListener listener){
        this.csListener = listener;
    }
    
    private KeyPadListener(String serialPortName)
    {
        try{
            port = new SerialPort(serialPortName);
            port.openPort();
            port.setParams(9600, 8, 1, 0);

            port.addEventListener(new SerialPortEventListener() {
                   
                @Override
                public void serialEvent(SerialPortEvent spe) {
                    try {
                        String[][] table = new String[][]{{"D", "#", "0", "*"},{"C", "9", "8", "7"},{"B", "6", "5", "4"},{"A", "3", "2", "1"}};
                        Thread.sleep(100);
                        String value = port.readString();
                        System.out.println("received: " + value);
                        if(value != null)
                        {
                            value = value.trim();
                            if( value.length() == 5)
                            {

                                String sub = value.substring(0, 2);
                                if(sub.equals("BU"))
                                {
                                    value = value.replace("BU", "");
                                    String[] indices = value.split(",");
                                    int i = Integer.parseInt(indices[0]);
                                    int j = Integer.parseInt(indices[1]);
                                    char key = table[i][j].charAt(0);
                                    System.out.println(key);
                                    bpListener.buttonPressed(key);
                                }
                                
                                
                            }
                            if(value.length() > 5){
                                String sub = value.substring(0, 2);
                                if(sub.equals("CR"))
                                {
                                    accountID = "";
                                    cardNumber = "";
                                    String[] hexcodes = value.substring(4).split(" ");
                                    for(int i = 0; i < hexcodes.length - 2; i++){
                                        accountID += (char)Integer.parseInt(hexcodes[i], 16);
                                    }
                                    for(int i = hexcodes.length - 2; i < hexcodes.length; i++){
                                        cardNumber += (char)Integer.parseInt(hexcodes[i], 16);
                                    }
                                    
                                    System.out.println(accountID);
                                    csListener.CardSwiped(accountID);
                                }
                            }
                        }
                        
                    } catch (SerialPortException | InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        catch(Exception e)
        {

        }
    }
    
    public String getAccountID(){
        return this.accountID;
    }
    
    public void WriteDataToCard(byte[] data) throws Exception{
        if(data.length != 16)
            throw new Exception("data must contain 16 bytes");
        byte[] buffer = new byte[19];
        
        System.arraycopy(data, 0, buffer, 2, data.length);
        buffer[0] = (byte) 0x7F;
        buffer[1] = (byte) 0x70;
        buffer[18]= (byte) 0x7F;
        port.writeBytes(buffer);
    }
    public void ResetCardReader() throws SerialPortException{
        
        byte[] buffer = new byte[19];
        buffer[0] = (byte) 0x10;
        buffer[1] = (byte) 0x7F;
        port.writeBytes(buffer);
    }

    public String getCardNumber() {
        return cardNumber;
    }
    
}
