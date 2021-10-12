namespace CarRentals.CarChanges
{
    public class Change<T>
    {
        public ChangeType Type { get; set; }

        public T Payload { get; set; }
    }
}
