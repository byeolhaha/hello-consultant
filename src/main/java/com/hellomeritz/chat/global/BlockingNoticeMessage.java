package com.hellomeritz.chat.global;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BlockingNoticeMessage {

    CHINA("因为信件中含有违禁字样，所以被删除了。 禁用词 :"),
    US("The message has been erased because it contains ban words. ban words : "),
    UK("The message has been erased because it contains ban words. ban words : "),
    RUSSIAN("Это сообщение было удалено, потому что оно содержит запрещенное слово. Запрещенное слово:"),
    JAPANESE("メッセージに禁止単語が含まれているため、削除されました。 禁止単語:"),
    KOREAN("메시지에 금지 단어가 포함되어 있기 때문에 삭제되었습니다. 금지 단어:"),
    FRENCH("Le message a été supprimé car il contient un mot interdit. Mot interdit:"),
    SPANISH("Fue eliminado porque el mensaje contenía palabras prohibidas. Palabra prohibida:"),
    ITALIAN("Il messaggio è stato cancellato perché contiene una parola vietata. VIETATO ENTRARE:");

    private String message;

    BlockingNoticeMessage(String message) {
        this.message = message;
    }

    public static String getBlockingNoticeMessage(SourceLanguage language) {
        return Arrays.stream(BlockingNoticeMessage.values())
            .filter(blockingNoticeMessage -> blockingNoticeMessage.name().equals(language.name()))
            .map(blockingNoticeMessage -> blockingNoticeMessage.message)
            .findFirst()
            .orElseThrow(()-> new IllegalArgumentException("일치하는 언어에 대한 안내 메세지가 없습니다."));
    }

}
