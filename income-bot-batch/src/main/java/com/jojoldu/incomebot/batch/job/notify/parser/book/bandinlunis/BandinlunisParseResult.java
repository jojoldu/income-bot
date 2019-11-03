package com.jojoldu.incomebot.batch.job.notify.parser.book.bandinlunis;

import com.jojoldu.incomebot.batch.job.notify.parser.book.BookParseResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.jojoldu.incomebot.batch.util.NumberUtils.toAbsCommaNumber;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public class BandinlunisParseResult implements BookParseResult {
    public static final BandinlunisParseResult EMPTY = new BandinlunisParseResult(0);
    private static final String TEXT_FORMAT = "[반디앤루니스] \"{goods}\"의 판매지수가 {addScore} 되어 현재 {newScore} 를 달성했습니다.";

    private final long salesPoint;

    @Override
    public String getMessage(long beforeScore, String goods) {
        long changeScore = salesPoint - beforeScore;
        String code = changeScore >= 0 ? "+" : "-";

        return TEXT_FORMAT
                .replaceAll("\\{goods\\}", goods)
                .replaceAll("\\{addScore\\}", code + toAbsCommaNumber(changeScore))
                .replaceAll("\\{newScore\\}", toAbsCommaNumber(salesPoint));
    }
}
