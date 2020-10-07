package share.money.user.api.shared;

public class ModelMapper {
    private static org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

    public static <T> T map(Object input, Class<T> output) {
        return modelMapper.map(input, output);
    }
}
