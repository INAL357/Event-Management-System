package com.app.eventmanagement.services;

public interface QRCodeService {

    public byte[] generateQRCode(String text) throws Exception;
}
