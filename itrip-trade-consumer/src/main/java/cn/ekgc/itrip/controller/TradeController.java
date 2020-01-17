package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.enums.SuccessEnum;
import cn.ekgc.itrip.hotel.transport.HotelOrderTransport;
import cn.ekgc.itrip.pojo.entity.HotelOrder;
import cn.ekgc.itrip.pojo.vo.PersonalOrderRoomVO;
import cn.ekgc.itrip.pojo.vo.ResponseResult;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController("tradeController")
@RequestMapping("/trade/api")
public class TradeController extends BaseController {
	@Autowired
	private HotelOrderTransport hotelOrderTransport;

	@RequestMapping(value = "/prepay/{orderNo}", method = RequestMethod.GET)
	public ResponseResult<Object> payOrder(@PathVariable("orderNo") String orderNo) throws Exception {
		// 通过订单编号查找对应的订单信息
		HotelOrder hotelOrder = hotelOrderTransport.getHotelOrderByNo(orderNo);

		// 判断该订单是否存在，另外订单处于未支付状态
		if (hotelOrder != null && hotelOrder.getOrderStatus() == 0) {
			// 查询房间信息
			PersonalOrderRoomVO roomVO = hotelOrderTransport.getPersonalOrderRoomInfo(hotelOrder.getId());
			// 该订单可以进行支付
			// 获得订单金额
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String total_amount = decimalFormat.format(hotelOrder.getPayAmount());
			// 订单编号使用当前时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String out_trade_no = dateFormat.format(new Date()) + System.currentTimeMillis();
			String product_code = orderNo;
			String subject = hotelOrder.getHotelName();
			String body = roomVO.getRoomTitle() + "，" + hotelOrder.getCount() + "间，" + hotelOrder.getBookingDays() + "天";

			// 开始支付
			AlipayClient alipayClient = new DefaultAlipayClient(
					"https://openapi.alipaydev.com/gateway.do",
					"2016101900726066",
					"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCg5PDS/UVALrqhep/YJoWIGESEMG1q49AxJ3Hu/cVtFurvHwcrfnoAgcL8D2H6lBPtsUIt63qfJ6pHhFNZRaAR/xDphEIfbX2HINk5SbXMxN6W5pSIvUvgq0CMSv6MsvLmE58/POQMQRS+ZUENX342mtueqKrUGfQdwtnkIz6q7UA9FnjsYlG+268CjC1r6XHZf/qGxZ0Og7LrulAzgxBetcPwGo4ML1z5Mygh5t7nPhJft4R6o8msadPUHU2ZioRhg+hzLJ1b9naLyuD+Ff2Jm0fuLFos6r1emG39jv+7mEO7DARut35BF6px1pEHTVQ0+431kwogbcFKyQWLsabnAgMBAAECggEAV3ylxtSZN1ExgmTfTfWEXoBzoT2EAb9mkZPVTBMJq0vu659/kpLvXB8zG0RyDoM+Yt0kEzwcLTawTLsTZ1OIusBrPc8A9X3f5/5gn9oI1ROnxX4T/laQT9ZO/vo4d+FGWv0IdqZxiohI806zlMX8J00FZrxoR3TlXNl7CaZwiRgikRj+7TnUiT/Km3pRwzjkU5/Vl/hFTMnMn/fIcRLCYRXVRRKvBIcqDdt9kIF74ieOXxS4hAfg7Kdbyy22A5R64ojQjAvMMk/4uPAvYXtJMX25d2SXsPCevKaCf5ZfK9CtNx/bDB1RMsYdlPNKpFBA9I7409zl1fS+6qZ5OFpt4QKBgQDluJxN4zJwAEHTUgfWeehJ+G0NvqyWzubYsXXSLEK/BLii3UqCJWv2YFFWToaexMfnM2kHw3wGNdSBFI0zQY3F75uG/eL04J9fnN0ueeBY4z9rS1Qs1wYV7wuZqB6wrljqwDnNZHLS7qrWQRb+Viw5KnxnH1a+ioXQYLlnvzAe7QKBgQCzTLwHTWYBgNjgAUjKtR/vJxI5/nNxvQwiBg2bBAnlzC4a/VG6EeD6sKKFRpTVL4Lk+7jlTi7/dUJradXrnICXUKKA+Wih+Pflwt8KSjQDySooZvmEKWFlGzQ3gwjt4g9S8mTkOeEmiXUhdyvoJxDNxEwx0m4cCZMUlCmB7zEOowKBgQCw8z1jOaj2YRxTXRKwgQMPR48pUUC8ge+L51ZsaK+ZWFepDHCCQ7Uf+R1uL9q+nbGmRBGZa1kzp29jZqNFYi+E0oaouxSBsCzTh49QZ40KqzGEwme6in8jkhkLaVcKnaHcYemkbsWL+VAa7dR6YYJ7HmDpRliZOTbORlNKuRimGQKBgDkEmPLViS/HMrChl+1mhf4+dYxdv4aW8MnNFPyL3z3lnW/VyOycDOiLzVMGrAPTdROufRDmuQlh17pi0LV5cmcsLFFJfx2rF+JIAze5eysNCMwKWyTNJ/zIFuXkVK2ZavgcvzrYsSHZKpd2106fRbe5yeU6Huvpm406xKxeTUJzAoGAHYl3D1/G4e6a9LxXrO4xZNmkhrUsVG4TedcWty7WPyAZlZaz80umc4eEnuQB6o4k3m3kwKyVHeUgTVj1r99hDs3ScQbh2+aXcaGsRtAwRgR2xlITFuyqh+ExkH14KJncEzNOeKyUbUFglezPRkVb6mV1khOnFl+29dYLrhCofJo=",
					"json",
					"UTF-8",
					"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkFarLSKZKWp0OXoXu59FJamGcJWVyIwcmq7/mGAi/s24TgfGyXT1pIgLNnDEyXn9UCA/nTILc0AoCVB2/tcMWolJdRrrQb13QRgua+Te5ohoaj27QV9wsKjGaqxk70cCa8TEsdLdiEWBJFrWqXeRM8FvMNVj+gdkSEvDFSB6efhCdScI0qwpee7XXb75SrHDLpjYTfn47P7hp1wBdHP8xElNERKc6wILWAh6/XAsnmMnRXQ/yEmvYp7LP83Fj0uQvRlWvliGfpgc5xzaDK1C4vqwOX27JPt3A9ZPqVX+GT9Ky6Jftyn6La+eIaVQp2No74bSrtc+n+jRtthYNmxEUwIDAQAB",
					"RSA2"
			); //获得初始化的AlipayClient
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
			alipayRequest.setReturnUrl("http://localhost/itrip");
			alipayRequest.setNotifyUrl("http://itrip.project.bdqn.cn/trade/api/notify/" + hotelOrder.getId());//在公共参数中设置回跳和通知地址

			String json = "{\"out_trade_no\":\"" + out_trade_no
					+ "\", \"product_code\":\"FAST_INSTANT_TRADE_PAY\", "
					+ "\"total_amount\":\"" + total_amount
					+ "\",\"subject\":\"" + subject
					+ "\",\"body\":\"" + body + "\","
					+ "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\",\"extend_params\":{}}";
			alipayRequest.setBizContent(json);//填充业务参数
			String form="";
			try {
				form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(form);//直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		}
		return new ResponseResult<>(SuccessEnum.SUCCESS_TRUE);
	}
}
