package com.iCourse.course.web.controller.teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iCourse.common.bean.PageInfo;
import com.iCourse.common.util.BaseController;
import com.iCourse.course.bean.Exercises;
import com.iCourse.course.bean.DTO.ExercisesQueryDTO;
import com.iCourse.course.service.ExercisesService;

@Controller
@RequestMapping("/teacher")
@CrossOrigin
public class ExercisesTeaController extends BaseController<Exercises> {

	@Autowired
	private ExercisesService exercisesService;

	@PostMapping("/getExercises")
	@ResponseBody
	public Object getExercises(ExercisesQueryDTO query, String ids, PageInfo<Exercises> pageInfo,
			HttpServletRequest req) {
		start();
		List<Long> chapter_ids = new ArrayList<Long>();
		if (!StringUtils.isEmpty(ids)) {
			String[] id = ids.split(",");
			for (String i : id) {
				chapter_ids.add(Long.parseLong(i));
			}
			query.setChapter_ids(chapter_ids);
		}

		pageInfo.initPage(1, 8);
//		Pageable pageable = PageRequest.of(pageInfo.getPageno() - 1, pageInfo.getPagesize(), pageInfo.getSort());
		Page<Exercises> data = exercisesService.getExercises(query.getWhere(query), (Pageable) pageInfo.getPageable());

		pageInfo.setReault(data);

		success(pageInfo);

		return end();
	}

	@PostMapping("/exercises")
	@ResponseBody
	public Object saveExercises(@RequestBody Exercises exercises) throws Exception {

		start();
		if ("填空题".equals(exercises.getExercises_type())) {
			String exercises_content = exercises.getExercises_content();

			int begin = exercises_content.indexOf("【");
			if (begin == 0) {
				exercises_content = "  " + exercises_content;
				begin = exercises_content.indexOf("【");
			}
			List<String> answer = new ArrayList<String>();
			do {
				int end = exercises_content.indexOf("】", begin);

				answer.add(exercises_content.substring(begin + 1, end));

				begin = exercises_content.indexOf("【", end);
			} while (begin != -1);
			String temp, short_answer = "";
			for (String str : answer) {
				short_answer += str + ",";
				temp = exercises_content.replaceAll(str, "");
				exercises_content = temp;
			}
			exercises.setExercises_content(exercises_content);
			exercises.getExercisesAnswer().setShort_answer(short_answer);
		}
		if ("判断题".equals(exercises.getExercises_type())) {
			if (exercises.getExercisesAnswer().getJudge_answer())
				exercises.getExercisesAnswer().setShort_answer("对");
			else
				exercises.getExercisesAnswer().setShort_answer("错");
		}
		exercises.setCreate_time(new Date());
//		Exercises exercises = JsonXMLUtils.map2obj((Map<String, Object>)models.get("user"),Exercises.class); 
		exercisesService.save(exercises);
		success();
		return end();
	}

	@PostMapping("/getExercisesByChapters")
	@ResponseBody
	public Object getExercisesByChapters(ExercisesQueryDTO query, String ids) {
		start();
		List<Long> chapter_ids = new ArrayList<Long>();
		if (!StringUtils.isEmpty(ids)) {
			String[] id = ids.split(",");
			for (String i : id) {
				chapter_ids.add(Long.parseLong(i));
			}
			query.setChapter_ids(chapter_ids);
		}

		List<Exercises> datas = exercisesService.getExercisesByChapters(query.getWhere(query));
		success(datas);

		return end();
	}
}
