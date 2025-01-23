import phonenumbers
from phonenumbers import geocoder, carrier, timezone

def analyze_phone_number(phone_number):
    # Parse the phone number
    parsed_number = phonenumbers.parse(phone_number, "US")

    # Check if the phone number is valid
    is_valid = phonenumbers.is_valid_number(parsed_number)

    # Get the country of the phone number
    country = geocoder.description_for_number(parsed_number, "en")

    # Get the carrier of the phone number
    carrier_name = carrier.name_for_number(parsed_number, "en")

    # Get the timezone of the phone number
    timezones = timezone.time_zones_for_number(parsed_number)

    # Print the details
    print("Phone Number:", phone_number)
    print("Valid:", is_valid)
    print("Country:", country)
    print("Carrier:", carrier_name)
    print("Timezones:", timezones)

# Example usage
phone_number = "+1720357595"  # Replace with the actual phone number
analyze_phone_number(phone_number)