package com.runninggo.toy.service.course;

import com.runninggo.toy.dao.course.CourseDao;
import com.runninggo.toy.domain.CommonResponseDto;
import com.runninggo.toy.myinfo.MyInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.runninggo.toy.constant.MessageConstant.*;
import static com.runninggo.toy.domain.course.CourseRequestDto.*;
import static com.runninggo.toy.domain.course.CourseResponseDto.*;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final MessageSourceAccessor messageSource;
    private final MyInfo myInfo;

    public CourseServiceImpl(MyInfo myInfo, MessageSourceAccessor messageSource, CourseDao courseDao) {
        this.myInfo = myInfo;
        this.messageSource = messageSource;
        this.courseDao = courseDao;
    }

    public String messageSource(String messageCode) {
        return messageSource.getMessage(messageCode);
    }

    @Override
    public CommonResponseDto getSubwayInfo(String subwayName) throws Exception {
        CommonResponseDto response = new CommonResponseDto<>(messageSource(SUCCESS_CODE), messageSource(SUCCESS));
        List<getSubwayInfoResDto> lineList = new ArrayList<>();
        getSubwayInfoResDto dto;

        try {
            String encodeSubwayName = URLEncoder.encode(subwayName, "UTF-8");
            log.info("encodeSubwayName = {}, decodeSubwayName = {}", encodeSubwayName, URLDecoder.decode(encodeSubwayName));

            URL url = new URL("http://openapi.seoul.go.kr:8088/" +
                    myInfo.seoulApiKey + "/json/SearchInfoBySubwayNameService/1/5/" + encodeSubwayName + "/");

            //InputStreamReader : 바이트 단위 데이터를 문자(character) 단위 데이터로 처리할 수 있도록 변환해준다.
            //BufferedReader : Buffer(버퍼)를 통해 입력받은 문자를 쌓아둔 뒤 한 번에 문자열처럼 보내버리는 것.
            //readLine :  한 줄 전체를(공백 포함) 읽어 String으로 return
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();
            log.info("url StringType result = {}", result);

            JSONObject jObject = new JSONObject(result); //json 객체
            JSONObject sibsns = jObject.getJSONObject("SearchInfoBySubwayNameService"); //받아온 json데이터 중 최상위 object
            JSONArray row = sibsns.getJSONArray("row");

            // 베열 출력
            for (int i = 0; i < row.length(); i++) {
                JSONObject obj = row.getJSONObject(i);
                dto = new getSubwayInfoResDto(obj.getString("LINE_NUM"));
                lineList.add(dto);
                log.info("LINE_NUM = {}", lineList.get(i));
            }

            response.setResultList(lineList);
            return response;

        } catch (JSONException e) {
            log.error("JSONException >>>> getSubwayInfo : {}", e.getMessage());
            response.setReturnCode(messageSource(FAIL_CODE));
            response.setMessage(messageSource(FAIL));
            return response;
        } catch (Exception e) {
            log.error("error >>>> getSubwayInfo : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CommonResponseDto insertCourse(InsertCourseReqDto param) throws Exception {
        CommonResponseDto response = new CommonResponseDto(messageSource(SUCCESS_CODE), messageSource(SUCCESS));
        courseDao.insertCourse(param);
        return response;
    }

    @Override
    public CommonResponseDto getAllCourse(GetAllCourseReqDto param) throws Exception {
        CommonResponseDto<GetCourseResDto> response = new CommonResponseDto<>(messageSource(SUCCESS_CODE), messageSource(SUCCESS));
        List<GetCourseResDto> list = courseDao.getAllCourse(param);
        response.setResultList(list);
        response.setTotalCount(courseDao.getCourseTotalCount(param));
        return response;
    }
}
