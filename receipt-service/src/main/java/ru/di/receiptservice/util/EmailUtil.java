package ru.di.receiptservice.util;

import lombok.Getter;
import org.springframework.util.FileCopyUtils;
import ru.di.receiptservice.entity.Receipt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
public class EmailUtil {

    private final String FILENAME = "/static/receipt.html";
    private final String MAIL_BODY;

    public EmailUtil() {
        try (InputStream inputStream = getClass().getResourceAsStream(FILENAME)) {
            if (inputStream == null) {
                throw new IOException("receipt html file missing...");
            }

            Reader reader = new InputStreamReader(inputStream, UTF_8);
            MAIL_BODY = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String prepareMail(Receipt receipt, String name) {
        return MAIL_BODY.replace("{receipt_id}", receipt.getId().toString())
                .replace("{uuid}", receipt.getUuid().toString())
                .replace("{currency_code}", receipt.getCurrencyCode())
                .replace("{bicCd}", receipt.getBicCd())
                .replace("{receiver}", name)
                .replace("{date}", receipt.getDate().toString())
                .replace("{amount}", receipt.getAmount().toString());
    }

}
