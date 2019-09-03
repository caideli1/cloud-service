package com.cloud.model.razorpay;

import java.util.List;

/**
 * razorpay payment webhooks events response entity
 */
public class RazorpayEventEntity {
    /**
     * event : payment.authorized
     * entity : event
     * contains : ["payment"]
     * payload : {"payment":{"entity":{"id":"pay_6X6jcHoHdRdy79","entity":"payment","amount":50000,"currency":"INR","status":"authorized","amount_refunded":0,"refund_status":null,"method":"card","order_id":"order_6X4mcHoSXRdy79","card_id":"card_6GfX4mcIAdsfDQ","bank":null,"captured":true,"email":"gaurav.kumar@example.com","contact":"9123456780","description":"RazorpayPaymentDto Description","error_code":null,"error_description":null,"fee":200,"service_tax":10,"international":false,"notes":{"reference_no":"848493"},"vpa":null,"wallet":null}},"created_at":1400826760}
     */

    private String event;
    private String entity;
    private String account_id;
    private List<String> contains;
    private PayloadBean payload;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public List<String> getContains() {
        return contains;
    }

    public void setContains(List<String> contains) {
        this.contains = contains;
    }

    public static class PayloadBean {
        /**
         * payment : {"entity":{"id":"pay_6X6jcHoHdRdy79","entity":"payment","amount":50000,"currency":"INR","status":"authorized","amount_refunded":0,"refund_status":null,"method":"card","order_id":"order_6X4mcHoSXRdy79","card_id":"card_6GfX4mcIAdsfDQ","bank":null,"captured":true,"email":"gaurav.kumar@example.com","contact":"9123456780","description":"RazorpayPaymentDto Description","error_code":null,"error_description":null,"fee":200,"service_tax":10,"international":false,"notes":{"reference_no":"848493"},"vpa":null,"wallet":null}}
         * created_at : 1400826760
         */

        private PaymentBean payment;
        private int created_at;

        public PaymentBean getPayment() {
            return payment;
        }

        public void setPayment(PaymentBean payment) {
            this.payment = payment;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public static class PaymentBean {
            /**
             * entity : {"id":"pay_6X6jcHoHdRdy79","entity":"payment","amount":50000,"currency":"INR","status":"authorized","amount_refunded":0,"refund_status":null,"method":"card","order_id":"order_6X4mcHoSXRdy79","card_id":"card_6GfX4mcIAdsfDQ","bank":null,"captured":true,"email":"gaurav.kumar@example.com","contact":"9123456780","description":"RazorpayPaymentDto Description","error_code":null,"error_description":null,"fee":200,"service_tax":10,"international":false,"notes":{"reference_no":"848493"},"vpa":null,"wallet":null}
             */
            private RazorpayPaymentEntity entity;

            public RazorpayPaymentEntity getEntity() {
                return entity;
            }

            public void setEntity(RazorpayPaymentEntity entity) {
                this.entity = entity;
            }


        }
    }
}
