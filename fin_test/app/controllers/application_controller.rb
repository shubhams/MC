class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  before_action :require_token
  private
  	def require_token
  			@user=User.find_by_email(params[:email])
  			if params[:token].nil?
  				@stat={
  					status: "Invalid request format should contain token"
  				}.to_json
  				render :json => @stat
  			elsif params[:email].nil?
  				@stat={
  					status: "Invalid request format should contain email"
  				}.to_json
  				render :json => @stat		
  			elsif(@user.token!=params[:token])
  				@stat={
  					status: "Invalid request!Auth token does not match"
  				}.to_json
  				render :json => @stat
  			end
	end
end