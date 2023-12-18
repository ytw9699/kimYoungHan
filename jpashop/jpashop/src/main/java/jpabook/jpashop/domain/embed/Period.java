package jpabook.jpashop.domain.embed;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period   {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

   /* public boolean isWork(){
        return true;//스타트랑 엔드 비교해서 현재시간 기준으로 안에있는지
    }*/

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
