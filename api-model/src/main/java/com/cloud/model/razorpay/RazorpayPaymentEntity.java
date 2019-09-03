package com.cloud.model.razorpay;

/**
 * razorpay payment FETCH | capture response entity
 */
public class RazorpayPaymentEntity {
    /**
     * notes : {"orderid":"1234567890976666"}
     * fee : 2950
     * description : purchase description
     * created_at : 1550648804
     * amount_refunded : 0
     * bank : ICIC
     * error_description : null
     * captured : true
     * contact : 12345696302
     * invoice_id : null
     * currency : INR
     * id : pay_ByU70QfibJn2T2
     * international : false
     * email : walle@moneed.net
     * amount : 6000
     * refund_status : null
     * wallet : null
     * method : netbanking
     * vpa : null
     * tax : 450
     * card_id : null
     * error_code : null
     * order_id : null
     * entity : payment
     * status : captured
     */

    private int fee;
    private String description;
    private int created_at;
    private int amount_refunded;
    private String bank;
    private String error_description;
    private boolean captured;
    private String contact;
    private String invoice_id;
    private String currency;
    private String id;
    private boolean international;
    private String email;
    private int amount;
    private String refund_status;
    private String wallet;
    private String method;
    private String vpa;
    private int tax;
    private String card_id;
    private String error_code;
    private String order_id;
    private String entity;
    private String status;
    private NotesBean notes;

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(int amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public boolean isCaptured() {
        return captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isInternational() {
        return international;
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotesBean getNotes() {
        return notes;
    }

    public void setNotes(NotesBean notes) {
        this.notes = notes;
    }

    public static class NotesBean {
        /**
         * orderid : 1234567890976666
         */

        private String orderid;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
