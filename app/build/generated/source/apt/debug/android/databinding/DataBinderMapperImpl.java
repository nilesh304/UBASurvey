package android.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new ubasurvey.nawin.com.ubasurvey.DataBinderMapperImpl());
  }
}