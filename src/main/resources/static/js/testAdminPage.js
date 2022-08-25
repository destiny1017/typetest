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
    $('button[type="submit"]').click( (e) => {
        $(e.target).css("pointer-events", "none");
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
    
        // setting 삭제아이콘 hover시에만 나타나도록
    $(".answerInfo-div").hover( (e) => {
        answerInfoHoverInEvent(e);
    }, (e) => {
        answerInfoHoverOutEvent(e);
    });


    function indicatorDeleteEvent(e) {
        let targetNum = e.target.id.replace("indiDel", "");
        $("#indicatorDiv" + targetNum).hide();
        $("#indicatorDiv" + targetNum + ' input:not([type="hidden"])').val("");
    }

    function settingDeleteEvent(e) {
        let targetNum = e.target.id.replace("delSetting", "");
        $("#settingDiv" + targetNum).hide();
        $("#settingDiv" + targetNum + ' input:not([type="hidden"])').val("");
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
        $("#questionDiv" + targetNum + ' input:not([type="hidden"])').val("");
    };

    function answerDeleteEvent(e) {
        let targetNum = e.target.id.replace("delAnswer", "");
        $("#answerDiv" + targetNum).hide();
        $("#answerDiv" + targetNum + ' input:not([type="hidden"])').val("");
        $("#answerDiv" + targetNum + " option").remove();
    };
    
    function answerInfoHoverInEvent(e) {
        let targetId = e.currentTarget.id.replace("answerDiv", "");
        $("#delAnswer" + targetId).show();
    }

    function answerInfoHoverOutEvent(e) {
        let targetId = e.currentTarget.id.replace("answerDiv", "");
        $("#delAnswer" + targetId).hide();
    }
    

    $('input[name="answerType"]').trigger("change"); // 화면 최초 로딩 후 테스트 방식에 따른 tab3 요소 변경

    function indicatorAddEvent() {
        // 새롭게 추가될 indicatorDiv 내용
        let indicatorDiv =
        `
            <div class="indicator-div" id="indicatorDiv${indicatorList.length + 1}">
                <div class="indicator-title">
                    <p><span class="material-icons">edit</span>유형 지표 정보</p>
                    <span class="material-icons indicatorDelete" id="indiDel${indicatorList.length + 1}">close</span>
                </div>
                <div class="indicatorInfo-div">
                    <div class="indicatorElement-div">
                        <label>지표 번호</label>
                        <input type="text" class="form-control" name="indicatorList[${indicatorList.length}].indicatorNum">
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
    }

    function settingAddEvent(e) {
        // 새롭게 추가될 setting div 내용
        let targetNum = e.target.id.replace("settingAdd-", "");
        let settingDiv =
        `
            <div class="settingInfo-div"
                id="settingDiv-${targetNum}-${indicatorList[targetNum-1].indicatorSettings.length}">
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
                            <input type="text" class="q-num-input" name="questionList[${questionList.length}].num">
                            <div class="px-1"></div>
                            <input type="text" class="q-name-input" name="questionList[${questionList.length}].question">
                        </div>
                        <span class="material-icons questionDelete" id="questionDel${questionList.length + 1}">close</span>
                    </div>
                    <div class="questionContent-div collapse show" id="questionContentDiv${questionList.length + 1}">
                        <input type="hidden" name="questionList[${questionList.length}].id">
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

});