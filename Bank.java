/**
 * การโอนเงินระหว่างสองบัญชี — ไฟล์ที่นิสิตต้องแก้ (ส่วนที่ 2)
 *
 * การโอนต้องล็อกสองใบพร้อมกัน เพราะระหว่างที่หักจากบัญชีต้นทาง
 * แล้วยังไม่ทันเพิ่มให้ปลายทาง เงินจะ "หายไปจากระบบ" ชั่วขณะ
 * ถ้ามีใครมาอ่านยอดรวมตอนนั้นจะได้ตัวเลขที่ผิด
 *
 * ปัญหาคือ การถือล็อกสองใบพร้อมกัน คือสูตรสำเร็จของ deadlock
 */
public class Bank {

    private Bank() {
        // utility class — ห้ามสร้าง object
    }

    /**
     * โอนเงินจากบัญชีหนึ่งไปอีกบัญชีหนึ่ง
     *
     * @return true ถ้าโอนสำเร็จ, false ถ้าเงินต้นทางไม่พอ
     * @throws IllegalArgumentException ถ้า argument ไม่ถูกต้อง หรือโอนเข้าบัญชีตัวเอง
     */
    public static boolean transfer(Account from, Account to, int amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("accounts must not be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (from == to) {
            throw new IllegalArgumentException("cannot transfer to the same account");
        }
        Account first = from ;
        Account second = to ;
        if (from.id()>to.id()){
            first = to ;
            second = from ;
        }
    
    
        synchronized (first) {
            synchronized (second) {
                if (!from.withdraw(amount)) {
                    return false;
                }
                to.deposit(amount);
                return true;
            }
        }
    }
}
