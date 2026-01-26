package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.jiocoders.portfolio.util.AppConstants;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Generic response wrapper for all API responses")
public class JioResponse<T> {

	@Schema(description = "Success status (1 for success, 0 for error)", example = "1")
	private int status;

	@Schema(description = "Indicates if the request was successful")
	private boolean success;

	@Schema(description = "Response message", example = "Operation completed successfully")
	private String message;

	@Schema(description = "The actual response data")
	private T data;

	@Schema(description = "Error information if success is false")
	private JioError error;

	@Schema(description = "Response timestamp")
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();

	public static <T> JioResponse<T> success(T data, String message) {
		return JioResponse.<T>builder()
			.status(AppConstants.SUCCESS_STATUS)
			.success(true)
			.message(message)
			.data(data)
			.build();
	}

	public static <T> JioResponse<T> error(String message, JioError jioError) {
		return JioResponse.<T>builder()
			.status(AppConstants.ERROR_STATUS)
			.success(false)
			.message(message)
			.error(jioError)
			.build();
	}

}
