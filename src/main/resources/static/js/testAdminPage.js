function appendLoadingDiv() {
    let loadingDiv =
    `<div class="back-div">
        <div class="loading-div">
          <img src="/assets/images/loading2.gif">
        </div>
     </div>
    `;
    $("body").append(loadingDiv);
}

function deleteLoadingDiv() {
    $(".back-div").remove();
}


$(document).ready( () => {

    /**
     *  이벤트 연결
     */

    // tab클릭 시 내용 전환 이벤트
    $(".nav-link").click( (e) => {
        $('[id^="step"]').removeClass("active");
        $(`[id^="${e.target.id}"]`).addClass("active");
    });
    
    // submit 중복 클릭 막기
//    $('button[type="submit"]').click( (e) => {
//        $(e.target).css("pointer-events", "none");
//    });
    
    // 값 변경시 update
    $(".tab-contentsForm [name]").focusin( (e) => {
        prevValue = e.target.value;
    });

    $(".tab-contentsForm [name]").focusout( (e) => {   
        let nowValue = e.target.value;

        if(prevValue != nowValue) {
            let target = e.target.name;
            let targetUpdate = target.substr(0, target.lastIndexOf(".")) + ".updated";
            $(`[name="${targetUpdate}"]`).val(1);
        }

    });
    
    /**
     *  tab1 event
     */
    // 테스트 방식 변경
    $('input[name="answerType"]').change( (e) => {
        answerTypeChange();
    });

    
    /**
     *  tab2 event
     */
    // indicator 추가 클릭
    $("#addIndicator").click( () => {
        indicatorAddEvent();
    });

    // setting 추가 클릭
    $(".settingAdd").click( (e) => {
        settingAddEvent(e);
    });

    // indicator 삭제 클릭
    $(".indicatorDelete").click( (e) => {
        indicatorDeleteEvent(e);
    });

    // setting 삭제 클릭
    $(".delSetting").click( (e) => {
        settingDeleteEvent(e);
    });

    // setting 삭제아이콘 hover시에만 나타나도록
    $(".settingInfo-div").hover( (e) => {
        settingInfoHoverInEvent(e);
    }, (e) => {
        settingInfoHoverOutEvent(e);
    });
    
    /**
     *  tab3 event
     */
    // question 추가 클릭
    $("#addQuestion").click( () => {
        questionAddEvent();
    });
    
    // question 삭제 클릭
    $(".questionDelete").click( (e) => {
        questionDeleteEvent(e);
    });
    
    // answer 추가 클릭
    $(".answerAdd").click( (e) => {
        answerAddEvent(e);
    });

    // answer 삭제 클릭
    $(".answerDelete").click( (e) => {
        answerDeleteEvent(e);
    });
    
    // answer 삭제아이콘 hover시에만 나타나도록
    $(".answerInfo-div").hover( (e) => {
        answerInfoHoverInEvent(e);
    }, (e) => {
        answerInfoHoverOutEvent(e);
    });
    
    /**
     *  tab4 event
     */
    // typeInfo 추가 클릭
    $("#addTypeInfo").click( () => {
        typeInfoAddEvent();
    });
    
    // typeInfo 삭제 클릭
    $(".typeInfoDelete").click( (e) => {
        typeInfoDeleteEvent(e);
    });
    
    // typeImage 추가 클릭
    $(".typeImageAdd").click( (e) => {
        typeImageAddEvent(e);
    });

    // typeImage 삭제 클릭
    $(".typeImageDelete").click( (e) => {
        typeImageDeleteEvent(e);
    });
    
    
    // typeImage 삭제아이콘 hover시에만 나타나도록
    $(".typeImageInfo-div").hover( (e) => {
        typeImageInfoHoverInEvent(e);
    }, (e) => {
        typeImageInfoHoverOutEvent(e);
    });
    
    // typeDescription 추가 클릭
    $(".typeDescriptionAdd").click( (e) => {
        typeDescriptionAddEvent(e);
    });

    // typeDescription 삭제 클릭
    $(".typeDescriptionDelete").click( (e) => {
        typeDescriptionDeleteEvent(e);
    });
    
    // typeDescription 삭제아이콘 hover시에만 나타나도록
    $(".typeDescriptionInfo-div").hover( (e) => {
        typeDescriptionInfoHoverInEvent(e);
    }, (e) => {
        typeDescriptionInfoHoverOutEvent(e);
    });
    
    
    $('input[name="answerType"]').trigger("change"); // 화면 최초 로딩 후 테스트 방식에 따른 tab3 요소 변경
    

    // 로딩 표시 삭제
    deleteLoadingDiv();
    
    // 알림 메시지 있을 경우 띄우기
    if(inactive) {
        alert("지표정보가 삭제되어 테스트가 비활성화 되었습니다.\n" +
              "질문/답변 및 결과유형 정보를 확인하고 다시 활성화해주시기 바랍니다.");
    }
    
    // submit 유효성 체크
    $("form").submit( (e) => {
        if(formCheck(e)) {
            $('button[type="submit"]').css("pointer-events", "none");
            return true;
        } else return false;
    });
    
    // 테스트 기본정보 미등록 시 나머지탭 비활성화
    if($(".type-select").val() == "NEW") {
        $("#step2, #step3, #step4").each((i, v) => {$(v).addClass("disableTab")});
    }

});

function indicatorDeleteEvent(e) {
    let targetNum = e.target.id.replace("indiDel", "");
    $("#indicatorDiv" + targetNum).hide();
    $("#indicatorDiv" + targetNum + ' input:not([type="hidden"])').val(0);
    $(`[name="indicatorList[${targetNum - 1}].deleted"]`).val(1);
    indicatorSeq();
}

function settingDeleteEvent(e) {
    let targetNum = e.target.id.replace("delSetting", "");
    let targetIndexes = targetNum.split("-");
    $("#settingDiv" + targetNum).hide();
    $("#settingDiv" + targetNum + ' input:not([type="hidden"])').val(0);
    $(`[name="indicatorList[${targetIndexes[1] - 1}].indicatorSettings[${targetIndexes[2]}].deleted"]`).val(1);
}

function settingInfoHoverInEvent(e) {
    let targetId = e.currentTarget.id.replace("settingDiv", "");
    $("#delSetting" + targetId).show();
}

function settingInfoHoverOutEvent(e) {
    let targetId = e.currentTarget.id.replace("settingDiv", "");
    $("#delSetting" + targetId).hide();
}

function answerTypeChange() {
    if($("#answerType1").is(":checked")) {
        $(".answerContent-div").hide();
    } else {
        $(".answerContent-div").show();
    }
}


function questionDeleteEvent(e) {
    let targetNum = e.target.id.replace("questionDel", "");
    $("#questionDiv" + targetNum).hide();
    $("#questionDiv" + targetNum + ' input:not([type="hidden"])').val(0);
    $(`[name="questionList[${targetNum - 1}].deleted"]`).val(1);
    questionSeq();
};

function answerDeleteEvent(e) {
    let targetNum = e.target.id.replace("delAnswer", "");
    let targetIndexes = targetNum.split("-"); // delAnswer-n-n
    $("#answerDiv" + targetNum).hide();
    $("#answerDiv" + targetNum + ' input:not([type="hidden"])').val(0);
    $("#answerDiv" + targetNum + " option").remove();
    $(`[name="questionList[${targetIndexes[1] - 1}].answerList[${targetIndexes[2]}].deleted"]`).val(1);
};

function answerInfoHoverInEvent(e) {
    let targetId = e.currentTarget.id.replace("answerDiv", "");
    $("#delAnswer" + targetId).show();
}

function answerInfoHoverOutEvent(e) {
    let targetId = e.currentTarget.id.replace("answerDiv", "");
    $("#delAnswer" + targetId).hide();
}


function typeInfoDeleteEvent(e) {
    let targetNum = e.target.id.replace("typeInfoDel", "");
    $("#typeInfoDiv" + targetNum).hide();
    $("#typeInfoDiv" + targetNum + ' input:not([type="hidden"])').val(0);
    $(`[name="typeInfoList[${targetNum - 1}].deleted"]`).val(1);
};

function typeImageDeleteEvent(e) {
    let targetNum = e.target.id.replace("delTypeImage", "");
    let targetIndexes = targetNum.split("-");
    $("#typeImageDiv" + targetNum).hide();
    $("#typeImageDiv" + targetNum + ' input:not([type="hidden"])').val(0);
    $("#typeImageDiv" + targetNum + " option").remove();
    $(`[name="typeInfoList[${targetIndexes[1] - 1}].typeImageList[${targetIndexes[2]}].deleted"]`).val(1);
    
    let index = targetNum.substr(1, targetNum.lastIndexOf("-")-1) - 1;
    typeImageSeq(index);
};

function typeImageInfoHoverInEvent(e) {
    let targetId = e.currentTarget.id.replace("typeImageDiv", "");
    $("#delTypeImage" + targetId).show();
}

function typeImageInfoHoverOutEvent(e) {
    let targetId = e.currentTarget.id.replace("typeImageDiv", "");
    $("#delTypeImage" + targetId).hide();
}

function typeDescriptionDeleteEvent(e) {
    let targetNum = e.target.id.replace("delTypeDescription", "");
    let targetIndexes = targetNum.split("-");
    $("#typeDescriptionDiv" + targetNum).hide();
    $("#typeDescriptionDiv" + targetNum + ' input:not([type="hidden"])').val(0);
    $("#typeDescriptionDiv" + targetNum + " option").remove();
    $(`[name="typeInfoList[${targetIndexes[1] - 1}].typeDescriptionList[${targetIndexes[2]}].deleted"]`).val(1);
    
    let index = targetNum.substr(1, targetNum.lastIndexOf("-")-1) - 1;
    typeDescriptionSeq(index);
};

function typeDescriptionInfoHoverInEvent(e) {
    let targetId = e.currentTarget.id.replace("typeDescriptionDiv", "");
    $("#delTypeDescription" + targetId).show();
}

function typeDescriptionInfoHoverOutEvent(e) {
    let targetId = e.currentTarget.id.replace("typeDescriptionDiv", "");
    $("#delTypeDescription" + targetId).hide();
}


function indicatorAddEvent() {
    // 새롭게 추가될 indicatorDiv 내용
    let indicatorDiv =
    `
        <div class="indicator-div" id="indicatorDiv${indicatorList.length + 1}">
            <div class="indicator-title">
                <p><span class="material-icons">edit</span>유형 지표 정보</p>
                <span class="material-icons indicatorDelete" id="indiDel${indicatorList.length + 1}">close</span>
            </div>
            <input type="hidden" name="indicatorList[${indicatorList.length}].id">
            <input type="hidden" name="indicatorList[${indicatorList.length}].updated" value="0">
            <input type="hidden" name="indicatorList[${indicatorList.length}].deleted" value="0">
            <div class="indicatorInfo-div">
                <div class="indicatorElement-div">
                    <label>지표 번호</label>
                    <input type="text" class="form-control" name="indicatorList[${indicatorList.length}].indicatorNum" readonly>
                </div>
                <div>
                    <label>지표 이름</label>
                    <input type="text" class="form-control" name="indicatorList[${indicatorList.length}].indicatorName">
                </div>
            </div>
            <div class="setting-div">
                <div class="settingAdd-div" id="settingAdd-div-${indicatorList.length + 1}">
                    <span class="settingAdd" id="settingAdd-${indicatorList.length + 1}">+ 지표 정보 추가</span>
                </div>
            </div>
        </div>
    `;
    // 추가버튼 위에 새 div 추가
    $("#addIndicator").before(indicatorDiv);

    // 요소 개수 카운트를 위해 indicator배열에 빈 객체 추가
    indicatorList.push({indicatorSettings:[]});

    // 추가된 indicator 삭제버튼에 이벤트 연결
    $("#indiDel" + (indicatorList.length)).click( (e) => {
        indicatorDeleteEvent(e);
    });

    // 추가된 setting추가 버튼에 이벤트 연결
    $("#settingAdd-" + (indicatorList.length)).click( (e) => {
        settingAddEvent(e);
    });
    
    indicatorSeq();
}

function settingAddEvent(e) {
    // 새롭게 추가될 setting div 내용
    let targetNum = e.target.id.replace("settingAdd-", "");
    let settingDiv =
    `
        <div class="settingInfo-div"
            id="settingDiv-${targetNum}-${indicatorList[targetNum-1].indicatorSettings.length}">
            <input type="hidden" name="indicatorList[${targetNum - 1}].indicatorSettings[${indicatorList[targetNum-1].indicatorSettings.length}].id">
            <input type="hidden" name="indicatorList[${targetNum - 1}].indicatorSettings[${indicatorList[targetNum-1].indicatorSettings.length}].updated" value="0">
            <input type="hidden" name="indicatorList[${targetNum - 1}].indicatorSettings[${indicatorList[targetNum-1].indicatorSettings.length}].deleted" value="0">
            <div class="settingElement-div">
                <label>지표 코드</label>
                <input type="text" class="form-control code-input"
                    name="indicatorList[${targetNum - 1}].indicatorSettings[${indicatorList[targetNum-1].indicatorSettings.length}].result">
            </div>
            <div class="settingElement-div">
                <label>지표 결정 점수</label>
                <input type="text" class="form-control"
                    name="indicatorList[${targetNum - 1}].indicatorSettings[${indicatorList[targetNum-1].indicatorSettings.length}].cuttingPoint">
            </div>
            <span class="material-icons delSetting" id="delSetting-${targetNum}-${indicatorList[targetNum-1].indicatorSettings.length}"
                    style="display: none;">
                delete
            </span>
        </div>
    `;
    // 추가버튼 위에 div 추가
    $("#settingAdd-div-" + targetNum).before(settingDiv);

    // 데이터 카운트를 위해 빈 객체 추가
    indicatorList[targetNum - 1].indicatorSettings.push({});

    // 추가된 삭제버튼에 삭제 이벤트 연결
    $(`#delSetting-${targetNum}-${indicatorList[targetNum-1].indicatorSettings.length - 1}`).click( (e) => {
        settingDeleteEvent(e);
    });

    // 추가된 삭제버튼에 hover 이벤트 연결
    $(`#settingDiv-${targetNum}-${indicatorList[targetNum-1].indicatorSettings.length - 1}`).hover( (e) => {
        settingInfoHoverInEvent(e);
    }, (e) => {
        settingInfoHoverOutEvent(e);
    });
}

function questionAddEvent() {
    // 새롭게 추가될 questionDiv 내용
    let questionDiv =
    `
            <div class="question-div" id="questionDiv${questionList.length + 1}">
                <div class="question-title">
                    <div class="questionHeadInfo-div">
                        <button class="fold-btn" type="button" data-bs-toggle="collapse" aria-expanded="true"
                                data-bs-target="#questionContentDiv${questionList.length + 1}"
                                aria-controls="questionContentDiv${questionList.length + 1}">
                            <span class="material-icons">
                            expand_more
                            </span>
                        </button>
                        <input type="text" class="q-num-input" name="questionList[${questionList.length}].num" placeholder="번호" readonly>
                        <div class="px-1"></div>
                        <textarea class="form-control" name="questionList[${questionList.length}].question" placeholder="질문내용"></textarea>
                    </div>
                    <span class="material-icons questionDelete" id="questionDel${questionList.length + 1}">close</span>
                </div>
                <div class="questionContent-div collapse show" id="questionContentDiv${questionList.length + 1}">
                    <input type="hidden" name="questionList[${questionList.length}].id">
                    <input type="hidden" name="questionList[${questionList.length}].updated" value="0">
                    <input type="hidden" name="questionList[${questionList.length}].deleted" value="0">
                    <div class="questionInfo-div">
                        <div class="indicatorElement-div">
                            <label>질문 이미지</label>
                            <input type="text" class="form-control" name="questionList[${questionList.length}].questionImage">
                        </div>
                    </div>
                    <div class="answer-div">
                        <div class="answerTitle-div">
                            <label>답변 정보</label>
                        </div>
                        <div class="answerAdd-div" id="answerAdd-div-${questionList.length + 1}">
                            <span class="answerAdd" id="answerAdd-${questionList.length + 1}">+ 답변 정보 추가</span>
                        </div>
                    </div>
                </div>
            </div>
    `;
    // 추가버튼 위에 새 div 추가
    $("#addQuestion").before(questionDiv);

    // 요소 개수 카운트를 위해 question 빈 객체 추가
    questionList.push({answerList:[]});

    // 추가된 question 삭제버튼에 이벤트 연결
    console.log("#questionDel" + (questionList.length));
    $("#questionDel" + (questionList.length)).click( (e) => {
        questionDeleteEvent(e);
    });

    // 추가된 answer 버튼에 이벤트 연결
    $("#answerAdd-" + (questionList.length)).click( (e) => {
        answerAddEvent(e);
    });
    
    questionSeq();
}

function answerAddEvent(e) {
    // 새롭게 추가될 answer div 내용
    let targetNum = e.target.id.replace("answerAdd-", "");
    let indicatorListOption = "";
    let tendencyListOption = "";
    // 지표 select박스 내용 만들기
    $.each(indicatorList, (i, val) => {
        let optionStr = `<option value="${val.id}">${val.indicatorName}</option>`;
        indicatorListOption += optionStr;
    });
    // 성향 select박스 내용 만들기
    $.each(tendencyNameList, (i, val) => {
        let optionStr = `<option value="${tendencyList[i]}">${val}</option>`;
        tendencyListOption += optionStr;
    });

    let answerDiv =
    `
        <div class="answerInfo-div" id="answerDiv-${targetNum}-${questionList[targetNum-1].answerList.length}">
            <span class="material-icons answerDelete" style="display: none;"
                  id="delAnswer-${targetNum}-${questionList[targetNum-1].answerList.length}">close</span>
            <input type="hidden" name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].id">
            <input type="hidden" name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].updated" value="0">
            <input type="hidden" name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].deleted" value="0">
            <div class="answerElement-div answerContent-div">
                <label>답변 이미지</label>
                <input type="text" class="form-control code-input"
                       name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].answerImage">
            </div>
            <div class="answerElement-div answerContent-div">
                <label>답변 내용</label>
                <input type="text" class="form-control"
                       name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].answer">
            </div>
            <div class="answerElement-div answerElementDetail-div">
                <div class="answerDetail-div">
                    <label>답변 점수</label>
                    <input type="text" class="form-control"
                           name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].point">
                </div>
                <div class="answerDetail-div">
                    <label>답변 지표</label>
                    <select class="answer-select form-select" 
                            name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].typeIndicatorId">
                        ${indicatorListOption}
                    </select>
                </div>
                <div class="answerDetail-div">
                    <label>답변 성향</label>
                    <select class="answer-select form-select" 
                            name="questionList[${targetNum-1}].answerList[${questionList[targetNum-1].answerList.length}].tendency">
                        ${tendencyListOption}
                    </select>
                </div>
            </div>
        </div>
    `;
    // 추가버튼 위에 div 추가
    $("#answerAdd-div-" + targetNum).before(answerDiv);

    // 데이터 카운트를 위해 빈 객체 추가
    questionList[targetNum - 1].answerList.push({});

    // 추가된 삭제버튼에 삭제 이벤트 연결
    $(`#delAnswer-${targetNum}-${questionList[targetNum-1].answerList.length - 1}`).click( (e) => {
        answerDeleteEvent(e);
    });

    // 추가된 삭제버튼에 hover 이벤트 연결
    $(`#answerDiv-${targetNum}-${questionList[targetNum-1].answerList.length - 1}`).hover( (e) => {
        answerInfoHoverInEvent(e);
    }, (e) => {
        answerInfoHoverOutEvent(e);
    });

    answerTypeChange();
}

function typeInfoAddEvent() {
    let typeInfoListOption = "";
    // 유형 select박스 내용 만들기
    $.each(typeInfoList, (i, val) => {
        let optionStr = `<option value="${val.id}">${val.typeCode}(${val.typeName})</option>`;
        typeInfoListOption += optionStr;
    });

    // 새롭게 추가될 typeInfoDiv 내용
    let typeInfoDiv =
    `
        <div class="typeInfo-div" id="typeInfoDiv${typeInfoList.length + 1}">
            <div class="typeInfo-title">
                <div class="typeInfoHeadInfo-div">
                    <button class="fold-btn" type="button" data-bs-toggle="collapse" aria-expanded="true"
                            data-bs-target="#typeInfoContentDiv${typeInfoList.length + 1}"
                            aria-controls="typeInfoContentDiv${typeInfoList.length + 1}">
                        <span class="material-icons">
                        expand_more
                        </span>
                    </button>
                    <input type="text" class="typeCode-input" name="typeInfoList[${typeInfoList.length}].typeCode" placeholder="CODE">
                    <div class="px-1"></div>
                    <input type="text" class="typeName-input" name="typeInfoList[${typeInfoList.length}].typeName" placeholder="유형명">
                </div>
                <span class="material-icons typeInfoDelete" id="typeInfoDel${typeInfoList.length + 1}">close</span>
            </div>
            <div class="typeInfoContent-div collapse show" id="typeInfoContentDiv${typeInfoList.length + 1}">
                <input type="hidden" name="typeInfoList[${typeInfoList.length}].id">
                <input type="hidden" name="typeInfoList[${typeInfoList.length}].updated" value="0">
                <input type="hidden" name="typeInfoList[${typeInfoList.length}].deleted" value="0">
                <div class="typeImage-div">
                    <div class="typeImageTitle-div">
                        <label>결과 이미지</label>
                    </div>

                    <div class="typeImageAdd-div" id="typeImageAdd-div-${typeInfoList.length + 1}">
                        <span class="typeImageAdd" id="typeImageAdd-${typeInfoList.length + 1}">+ 이미지 추가</span>
                    </div>
                </div>

                <div class="typeDescription-div">
                    <div class="typeDescriptionTitle-div">
                        <label>유형 설명</label>
                    </div>

                    <div class="typeDescriptionAdd-div" id="typeDescriptionAdd-div-${typeInfoList.length + 1}">
                        <span class="typeDescriptionAdd" id="typeDescriptionAdd-${typeInfoList.length + 1}">+ 설명 추가</span>
                    </div>
                </div>

                <div class="typeRelation-div">
                    <div class="typeRelationTitle-div">
                        <label>유형 관계 정보</label>
                    </div>
                    <div class="typeRelationInfo-div" id="typeRelationDiv-${typeInfoList.length + 1}">
                        <span class="material-icons typeRelationDelete" style="display: none;"
                              id="delTypeRelation-${typeInfoList.length + 1}">close</span>
                        <input type="hidden" name="typeInfoList[${typeInfoList.length}].typeRelation.id">
                        <input type="hidden" name="typeInfoList[${typeInfoList.length}].typeRelation.updated" value="0">
                        <input type="hidden" name="typeInfoList[${typeInfoList.length}].typeRelation.deleted" value="0">
                        <div class="typeRelationElement-div typeRelationContent-div">
                            <label>Best 유형</label>
                            <select class="typeRelation-select form-select" name="typeInfoList[${typeInfoList.length}].typeRelation.bestTypeId"">
                                <option value="">미지정</option>
                                ${typeInfoListOption}
                            </select>
                        </div>
                        <div class="typeRelationElement-div typeRelationContent-div">
                            <label>Worst 유형</label>
                            <select class="typeRelation-select form-select" name="typeInfoList[${typeInfoList.length}].typeRelation.worstTypeId"">
                                <option value="">미지정</option>
                                ${typeInfoListOption}
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
    // 추가버튼 위에 새 div 추가
    $("#addTypeInfo").before(typeInfoDiv);

    // 요소 개수 카운트를 위해 typeInfo 빈 객체 추가
    typeInfoList.push({typeImageList:[], typeDescriptionList:[]});

    // 추가된 typeInfo 삭제버튼에 이벤트 연결
    console.log("#typeInfoDel" + (typeInfoList.length));
    $("#typeInfoDel" + (typeInfoList.length)).click( (e) => {
        typeInfoDeleteEvent(e);
    });

    // 추가된 image 버튼에 이벤트 연결
    $("#typeImageAdd-" + (typeInfoList.length)).click( (e) => {
        typeImageAddEvent(e);
    });

    // 추가된 description 버튼에 이벤트 연결
    $("#typeDescriptionAdd-" + (typeInfoList.length)).click( (e) => {
        typeDescriptionAddEvent(e);
    });
}

function typeImageAddEvent(e) {
    // 새롭게 추가될 image div 내용
    let targetNum = e.target.id.replace("typeImageAdd-", "");
    let typeImageDiv =
    `
        <div class="typeImageInfo-div" id="typeImageDiv-${targetNum}-${typeInfoList[targetNum-1].typeImageList.length}">
            <span class="material-icons typeImageDelete" style="display: none;"
                  id="delTypeImage-${targetNum}-${typeInfoList[targetNum-1].typeImageList.length}">close</span>
            <input type="hidden" name="typeInfoList[${targetNum-1}].typeImageList[${typeInfoList[targetNum-1].typeImageList.length}].id">
            <input type="hidden" name="typeInfoList[${targetNum-1}].typeImageList[${typeInfoList[targetNum-1].typeImageList.length}].updated" value="0">
            <input type="hidden" name="typeInfoList[${targetNum-1}].typeImageList[${typeInfoList[targetNum-1].typeImageList.length}].deleted" value="0">
            <div class="typeImageDetail-div">
                <div class="typeImageNum-div typeImageContent-div">
                    <label>이미지 번호</label>
                    <input type="text" class="form-control code-input" readonly
                           name="typeInfoList[${targetNum-1}].typeImageList[${typeInfoList[targetNum-1].typeImageList.length}].imgNum">
                </div>
                <div class="typeImageUrl-div typeImageContent-div">
                    <label>이미지 URL</label>
                    <input type="text" class="form-control"
                           name="typeInfoList[${targetNum-1}].typeImageList[${typeInfoList[targetNum-1].typeImageList.length}].imageUrl">
                </div>
            </div>
            <div class="typeImagePicture-div">
                <label>이미지</label>
                <div class="">
                    <img src="">
                </div>
            </div>
        </div>
    `
    // 추가버튼 위에 div 추가
    $("#typeImageAdd-div-" + targetNum).before(typeImageDiv);

    // 데이터 카운트를 위해 빈 객체 추가
    typeInfoList[targetNum - 1].typeImageList.push({});

    // 추가된 삭제버튼에 삭제 이벤트 연결
    $(`#delTypeImage-${targetNum}-${typeInfoList[targetNum-1].typeImageList.length - 1}`).click( (e) => {
        typeImageDeleteEvent(e);
    });

    // 추가된 삭제버튼에 hover 이벤트 연결
    $(`#typeImageDiv-${targetNum}-${typeInfoList[targetNum-1].typeImageList.length - 1}`).hover( (e) => {
        typeImageInfoHoverInEvent(e);
    }, (e) => {
        typeImageInfoHoverOutEvent(e);
    });
    
    typeImageSeq(targetNum - 1);
}

function typeDescriptionAddEvent(e) {
    // 새롭게 추가될 description div 내용
    let targetNum = e.target.id.replace("typeDescriptionAdd-", "");
    let typeDescriptionDiv = 
    `
        <div class="typeDescriptionInfo-div" id="typeDescriptionDiv-${targetNum}-${typeInfoList[targetNum-1].typeDescriptionList.length}">
            <span class="material-icons typeDescriptionDelete" style="display: none;"
                  id="delTypeDescription-${targetNum}-${typeInfoList[targetNum-1].typeDescriptionList.length}">close</span>
            <input type="hidden" name="typeInfoList[${targetNum-1}].typeDescriptionList[${typeInfoList[targetNum-1].typeDescriptionList.length}].id">
            <input type="hidden" name="typeInfoList[${targetNum-1}].typeDescriptionList[${typeInfoList[targetNum-1].typeDescriptionList.length}].updated" value="0">
            <input type="hidden" name="typeInfoList[${targetNum-1}].typeDescriptionList[${typeInfoList[targetNum-1].typeDescriptionList.length}].deleted" value="0">
            <div class="typeDescriptionElement-div typeDescriptionContent-div">
                <label>설명 번호</label>
                <input type="text" class="form-control code-input" readonly
                       name="typeInfoList[${targetNum-1}].typeDescriptionList[${typeInfoList[targetNum-1].typeDescriptionList.length}].descNum">
            </div>
            <div class="typeDescriptionElement-div typeDescriptionContent-div">
                <label>설명</label>
                <textarea type="text" class="form-control"
                       name="typeInfoList[${targetNum-1}].typeDescriptionList[${typeInfoList[targetNum-1].typeDescriptionList.length}].description"></textarea>
            </div>
        </div>
    `
    // 추가버튼 위에 div 추가
    $("#typeDescriptionAdd-div-" + targetNum).before(typeDescriptionDiv);

    // 데이터 카운트를 위해 빈 객체 추가
    typeInfoList[targetNum - 1].typeDescriptionList.push({});

    // 추가된 삭제버튼에 삭제 이벤트 연결
    $(`#delTypeDescription-${targetNum}-${typeInfoList[targetNum-1].typeDescriptionList.length - 1}`).click( (e) => {
        typeDescriptionDeleteEvent(e);
    });

    // 추가된 삭제버튼에 hover 이벤트 연결
    $(`#typeDescriptionDiv-${targetNum}-${typeInfoList[targetNum-1].typeDescriptionList.length - 1}`).hover( (e) => {
        typeDescriptionInfoHoverInEvent(e);
    }, (e) => {
        typeDescriptionInfoHoverOutEvent(e);
    });
    
    typeDescriptionSeq(targetNum - 1);
}

    
function callEssential(testCode) {
    
    // 필수유형 추가돼 있는지 확인하여 없으면 추가해주는 로직
    for(essentialType of essentialTypeList) {
        let exist = false;

        $('input[name$="typeCode"]').each( (i,v) => {
            if(essentialType == v.value) {
                $(`#typeInfoDiv${i+1}`).addClass("essential-type");
                exist = true;
                return false;
            }
        });
        
        if(exist == false) {
            typeInfoAddEvent();
            $(`input[name="typeInfoList[${typeInfoList.length-1}].typeCode"]`).val(essentialType);
            $(`#typeInfoDiv${typeInfoList.length}`).addClass("essential-type");
        }
    }
    
}

// 테스트 활성화 시 필수 정보 등록 여부 체크
function enableActive() {
    let indicatorCnt = 0;
    let questionCnt = 0;
    
    for(indicator of indicatorList) {
        if(indicator.deleted == 0) indicatorCnt++;
    }
    
    if(indicatorCnt == 0) {
        alert("지표정보가 등록되지 않았습니다.");
        $("#active2").prop("checked", true);
        $("#step2").trigger("click");
        return;
    }
    
    for(question of questionList) {
        if(question.deleted == 0) questionCnt++;
    }
    
    if(questionCnt == 0) {
        alert("질문 정보가 등록되지 않았습니다.");
        $("#active2").prop("checked", true);
        $("#step3").trigger("click");
        return;
    }
    
    if(noAnswerQuestion()) {
        alert("답변정보가 등록되지 않은 질문이 있습니다.");
        $("#active2").prop("checked", true);
        $("#step3").trigger("click");
        return;
    }
    
    for(essentialType of essentialTypeList) {
        let exist = false;
        for(const [i, typeInfo] of typeInfoList.entries()) {
            if(essentialType == typeInfo.typeCode) {
                $(`#typeInfoDiv${i+1}`).addClass("essential-type");
                exist = true;
                break;
            }
        }

        if(exist == false) {
            $("#active2").prop("checked", true);
            alert("아직 세팅되지 않은 필수유형 정보가 있습니다.");
            $("#step4").trigger("click");
            return;
        }
    }
    
}

// 지표정보 자동 넘버링 함수
function indicatorSeq() {
    let cnt = 1;
    $('input[name$="indicatorNum"]').each((i,v) => {
        let indicator = v.name.substr(0, v.name.indexOf("."));
        if($(`input[name="${indicator}.deleted"]`).val() == 0) {
            v.value = cnt++;

            if(indicatorList[i].indicatorNum != v.value) {
                $(`input[name="${indicator}.updated"]`).val(1);
                console.log(indicator);        
            }
        }
    });
}

// 질문정보 자동 넘버링 함수
function questionSeq() {
    let cnt = 1;
    $('input[name$="num"]').each((i,v) => {
        let question = v.name.substr(0, v.name.indexOf("."));
        if($(`input[name="${question}.deleted"]`).val() == 0) {
            v.value = cnt++;

            if(questionList[i].questionNum != v.value) {
                $(`input[name="${question}.updated"]`).val(1);
                console.log(question);        
            }
        }
    });
}

// 이미지 정보 자동 넘버링 함수
function typeImageSeq(index) {
    let cnt = 0;
    let num = 1;
    while(true) {
        let $target = $(`input[name="typeInfoList[${index}].typeImageList[${cnt}].deleted"]`);
        if($target.length > 0) {
            
            if($target.val() == 0) {
                $target = $(`input[name="typeInfoList[${index}].typeImageList[${cnt}].imgNum"]`);
                $target.val(num++);
                if(typeInfoList[index].typeImageList[cnt].imgNum != $target.val()) {
                    $target = $(`input[name="typeInfoList[${index}].typeImageList[${cnt}].updated"]`);
                    $target.val(1);
                    console.log($target);
                }
            }
            cnt++;
        } else {
            break;
        }
        
    }
}

// 설명 정보 자동 넘버링 함수
function typeDescriptionSeq(index) {
    let cnt = 0;
    let num = 1;
    while(true) {
        let $target = $(`input[name="typeInfoList[${index}].typeDescriptionList[${cnt}].deleted"]`);
        if($target.length > 0) {
            
            if($target.val() == 0) {
                $target = $(`input[name="typeInfoList[${index}].typeDescriptionList[${cnt}].descNum"]`);
                $target.val(num++);
                if(typeInfoList[index].typeDescriptionList[cnt].imgNum != $target.val()) {
                    $target = $(`input[name="typeInfoList[${index}].typeDescriptionList[${cnt}].updated"]`);
                    $target.val(1);
                    console.log($target);
                }
            }
            cnt++;
        } else {
            break;
        }
        
    }
}

// 질문 empty 체크
function noAnswerQuestion() {
    let existEmpty = false;
    for(question of questionList) {
        if(question.answerList.length == 0) {
            existEmpty = true;
            $("#questionDiv" + question.num).addClass("no-answer");
        } 
    }
    return existEmpty;
}

// form 제출 유효성 체크 함수
function formCheck(e) {
    let valid = true;
    // hidden input과 필수항목 아닌 필드는 제외하고 선택
    $(`#${e.target.id} input[name]:not([type="hidden"], .optional)`).each( (i, v) => {
        if(v.type == "radio") {
            if($(`input[name="${v.name}"]:checked`).length == 0) {
                alert("아직 체크하지 않은 값이 있습니다.");
                valid = false;
                return false;
            }
        } else {
            if(v.value.length == 0) {
                if(confirm("입력하지 않은 값이 있습니다.")) {
                    setTimeout( () => {
                       $(v).focus();
                        location.href = "#" + v.id; 
                    }, 100);
                }
                
                valid = false;
                return false;
            }
        }
    });
    
    return valid;
}
