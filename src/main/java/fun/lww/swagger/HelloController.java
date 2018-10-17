package fun.lww.swagger;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping(value = "/abc", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "abc", httpMethod = "GET")
	public String abc(@ApiParam String a, @ApiParam Integer b, @ApiParam boolean c, @RequestHeader String token, String d) {
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(token);
		return "abc";
	}
}
