package com.arextest.agent.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author yongwuhe
 * @date 2022/11/01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mealrecomrestaurant {

    /**
     * id
     */
	private Integer id;

    /**
     * restaurant name
     */
	@TableField("restaurantName")
	private String restaurantName;

    /**
     * dish name
     */
	@TableField("dishName")
	private String dishName;

    /**
     * flavor id
     */
	@TableField("flavorId")
	private Integer flavorId;

    /**
     * price
     */
	private BigDecimal price;

    /**
     * score
     */
	private BigDecimal score;

    /**
     * last update time
     */
	@TableField("datachange_lasttime")
	private Timestamp datachangeLasttime;

	public Mealrecomrestaurant(String restaurantName, String dishName, Integer flavorId, BigDecimal price, BigDecimal score) {
		this.restaurantName = restaurantName;
		this.dishName = dishName;
		this.flavorId = flavorId;
		this.price = price;
		this.score = score;
	}

	@Override
	public String toString() {
		return "Mealrecomrestaurant{" +
				"id=" + id +
				", restaurantName='" + restaurantName + '\'' +
				", dishName='" + dishName + '\'' +
				", flavorId=" + flavorId +
				", price=" + price +
				", score=" + score +
				", datachangeLasttime=" + datachangeLasttime +
				'}';
	}
}
