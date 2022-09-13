var url_default_ks = "https://story.kakao.com/share?url=";
var url_default_fb = "https://www.facebook.com/sharer/sharer.php?u=";
var url_default_tw_txt = "https://twitter.com/intent/tweet?text=";
var url_default_tw_url = "&url=";
var url_default_band = "http://band.us/plugin/share?body=";
var url_route_band = "&route=";
var url_default_naver = "http://share.naver.com/web/shareView.nhn?url=";
var title_default_naver = "&title=";
var url_this_page = location.href;
var title_this_page = document.title;
var url_combine_ks = url_default_ks + url_this_page;
var url_combine_fb = url_default_fb + url_this_page;
var url_combine_tw = url_default_tw_txt + document.title + url_default_tw_url + url_this_page;
var url_combine_band = url_default_band + encodeURI(url_this_page)+ '%0A' + encodeURI(title_this_page)+'%0A' + '&route=tistory.com';
var url_combine_naver = url_default_naver + encodeURI(url_this_page) + title_default_naver + encodeURI(title_this_page);

function copyUrl() {
    var urlInput = document.getElementById("urlInput");
    urlInput.value = document.location.href;
    urlInput.select();
    document.execCommand("copy");
    urlInput.blur();
    alert("주소가 복사되었습니다.");
}

    function numberCounter(target_frame, target_number) {
    this.count = 0; this.diff = 0;
    this.target_count = parseInt(target_number);
    this.target_frame = document.getElementById(target_frame);
    this.timer = null;
    this.counter();
};

numberCounter.prototype.counter = function() {
    var self = this;
    this.diff = this.target_count - this.count;

    if(this.diff > 0) {
        self.count += Math.ceil(this.diff / 5);
    }

    this.target_frame.innerHTML = this.count.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    if(this.count < this.target_count) {
        this.timer = setTimeout(function() { self.counter(); }, 30);
    } else {
        clearTimeout(this.timer);
    }
};

$(document).ready( () => {
    Kakao.init('27140768c4ba57a677d8073e1792449c');
    Kakao.Share.createCustomButton({
      container: '#kakaotalk-sharing-btn',
      templateId: 80348,
      templateArgs: {
        'title': '제목',
        'description': '설명'
      }
    });
    console.log("Asd");
    new numberCounter("countAnimation", $("#playCount").val());
});